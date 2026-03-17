package com.freskoexpress.api.domain.iot;

import com.freskoexpress.api.domain.iot.dto.*;
import com.freskoexpress.api.domain.iot.event.AlertaEventPublisher;
import com.freskoexpress.api.domain.iot.factory.AlertaFactory;
import com.freskoexpress.api.domain.logistica.Vehiculo;
import com.freskoexpress.api.domain.logistica.VehiculoRepository;
import com.freskoexpress.api.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SensorService {

    private final LecturaSensorRepository lecturaRepository;
    private final AlertaRepository        alertaRepository;
    private final VehiculoRepository      vehiculoRepository;
    private final AlertaFactory           alertaFactory;        // Factory Method
    private final AlertaEventPublisher    alertaEventPublisher; // Observer

    public SensorService(LecturaSensorRepository lecturaRepository,
                         AlertaRepository alertaRepository,
                         VehiculoRepository vehiculoRepository,
                         AlertaFactory alertaFactory,
                         AlertaEventPublisher alertaEventPublisher) {
        this.lecturaRepository    = lecturaRepository;
        this.alertaRepository     = alertaRepository;
        this.vehiculoRepository   = vehiculoRepository;
        this.alertaFactory        = alertaFactory;
        this.alertaEventPublisher = alertaEventPublisher;
    }

    @Transactional
    public void procesarLectura(LecturaSensorRequest request) {
        Vehiculo vehiculo = vehiculoRepository.findById(request.idVehiculo())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehículo no encontrado: " + request.idVehiculo()));

        boolean fueraDeRango = esFueraDeRango(request.temperaturaC());

        LecturaSensor lectura = LecturaSensor.builder()
                .vehiculo(vehiculo)
                .idRuta(request.idRuta())
                .temperaturaC(request.temperaturaC())
                .humedadPct(request.humedadPct())
                .fueraDeRango(fueraDeRango)
                .build();

        // Rendezvous: persistir antes de evaluar alerta
        LecturaSensor saved = lecturaRepository.save(lectura);

        if (fueraDeRango) {
            // Factory crea la alerta con reglas de severidad correctas
            Alerta alerta = alertaFactory.crearAlertaTemperatura(saved, vehiculo);
            alertaRepository.save(alerta);

            // Observer publica el evento de forma asíncrona (no bloquea)
            alertaEventPublisher.publicar(alerta);
        }
    }

    @Transactional
    public AlertaResponse reconocerAlerta(Integer idAlerta) {
        Alerta alerta = alertaRepository.findById(idAlerta)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alerta no encontrada: " + idAlerta));
        alerta.setReconocida(true);
        return AlertaResponse.from(alertaRepository.save(alerta));
    }

    public List<AlertaResponse> alertasActivas() {
        return alertaRepository.findByReconocidaFalseOrderByFechaAlertaDesc()
                .stream().map(AlertaResponse::from).toList();
    }

    public List<AlertaResponse> alertasPorVehiculo(Integer idVehiculo) {
        return alertaRepository.findByVehiculoIdVehiculoOrderByFechaAlertaDesc(idVehiculo)
                .stream().map(AlertaResponse::from).toList();
    }

    private boolean esFueraDeRango(BigDecimal temp) {
        // Rango general para cadena de frío de alimentos perecederos
        return temp.compareTo(BigDecimal.ZERO) < 0
                || temp.compareTo(new BigDecimal("15")) > 0;
    }
}