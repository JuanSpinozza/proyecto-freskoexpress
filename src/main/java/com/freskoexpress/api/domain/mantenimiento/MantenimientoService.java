package com.freskoexpress.api.domain.mantenimiento;

import com.freskoexpress.domain.iot.Alerta;
import com.freskoexpress.domain.iot.AlertaRepository;
import com.freskoexpress.domain.iot.event.AlertaEventPublisher;
import com.freskoexpress.domain.iot.factory.AlertaFactory;
import com.freskoexpress.domain.logistica.Vehiculo;
import com.freskoexpress.domain.logistica.VehiculoRepository;
import com.freskoexpress.domain.mantenimiento.dto.*;
import com.freskoexpress.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final VehiculoRepository      vehiculoRepository;
    private final AlertaFactory           alertaFactory;          // Factory Method
    private final AlertaRepository        alertaRepository;
    private final AlertaEventPublisher    alertaEventPublisher;   // Observer

    @Transactional
    public MantenimientoResponse registrar(CrearMantenimientoRequest request) {
        Vehiculo vehiculo = vehiculoRepository.findById(request.idVehiculo())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Vehículo no encontrado: " + request.idVehiculo()));

        if (request.proxKm() != null && request.proxKm() <= request.kmEnServicio()) {
            throw new com.freskoexpress.shared.exception.BusinessException(
                "prox_km debe ser mayor al kilometraje actual");
        }

        Mantenimiento m = Mantenimiento.builder()
            .vehiculo(vehiculo)
            .tipo(request.tipo())
            .descripcion(request.descripcion())
            .kmEnServicio(request.kmEnServicio())
            .fechaServicio(request.fechaServicio())
            .proxKm(request.proxKm())
            .proxFecha(request.proxFecha())
            .build();

        return MantenimientoResponse.from(mantenimientoRepository.save(m));
    }

    /**
     * Tarea programada: cada día a las 8am verifica vehículos
     * próximos a mantenimiento y genera alertas automáticamente.
     * Usa AlertaFactory (Factory) + AlertaEventPublisher (Observer).
     */
    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void verificarMantenimientosProximos() {
        LocalDate limite = LocalDate.now().plusDays(7);

        // Por fecha
        mantenimientoRepository.findProximosPorFecha(limite).forEach(m -> {
            String motivo = "Próximo mantenimiento programado el " + m.getProxFecha();
            Alerta alerta = alertaFactory.crearAlertaMantenimiento(m.getVehiculo(), motivo);
            alertaRepository.save(alerta);
            alertaEventPublisher.publicar(alerta);
        });

        // Por km (margen de 1000 km)
        mantenimientoRepository.findProximosPorKm(1000).forEach(m -> {
            String motivo = "Kilometraje actual " + m.getVehiculo().getKilometraje()
                + " km. Próximo servicio a los " + m.getProxKm() + " km";
            Alerta alerta = alertaFactory.crearAlertaMantenimiento(m.getVehiculo(), motivo);
            alertaRepository.save(alerta);
            alertaEventPublisher.publicar(alerta);
        });
    }

    public List<MantenimientoResponse> historialPorVehiculo(Integer idVehiculo) {
        return mantenimientoRepository
            .findByVehiculoIdVehiculoOrderByFechaServicioDesc(idVehiculo)
            .stream().map(MantenimientoResponse::from).toList();
    }
}
