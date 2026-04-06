package com.freskoexpress.api.domain.logistica.facade;

import com.freskoexpress.api.domain.auth.Usuario;
import com.freskoexpress.api.domain.auth.UsuarioRepository;
import com.freskoexpress.api.domain.logistica.Ruta;
import com.freskoexpress.api.domain.logistica.RutaRepository;
import com.freskoexpress.api.domain.logistica.Vehiculo;
import com.freskoexpress.api.domain.logistica.VehiculoRepository;
import com.freskoexpress.api.domain.logistica.dto.CrearRutaRequest;
import com.freskoexpress.api.domain.logistica.dto.RutaResponse;
import com.freskoexpress.api.domain.pedido.Pedido;
import com.freskoexpress.api.domain.pedido.PedidoRepository;
import com.freskoexpress.api.shared.enums.EstadoRuta;
import com.freskoexpress.api.shared.enums.RolUsuario;
import com.freskoexpress.api.shared.exception.BusinessException;
import com.freskoexpress.api.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Facade Pattern — Planificación de rutas de entrega.
 *
 * Encapsula y orquesta el proceso complejo de creación de una ruta:
 *   1. Verificar vehículo disponible
 *   2. Verificar que el usuario tenga rol conductor
 *   3. Cargar pedidos pendientes
 *   4. (Placeholder) Consultar API externa de tráfico
 *   5. Ordenar paradas según ventanas de entrega
 *   6. Persistir ruta + asignar pedidos
 *   7. Liberar/bloquear disponibilidad del vehículo
 *   8. (Placeholder) Notificar conductor
 *
 * El RutaController y el RutaService solo llaman a este Facade.
 * No conocen ninguno de los 8 pasos internos.
 */
@Component
public class PlanificacionFacade {

    private static final Logger log = LoggerFactory.getLogger(PlanificacionFacade.class);

    private final RutaRepository     rutaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository  usuarioRepository;
    private final PedidoRepository   pedidoRepository;

    public PlanificacionFacade(RutaRepository rutaRepository,
                               VehiculoRepository vehiculoRepository,
                               UsuarioRepository usuarioRepository,
                               PedidoRepository pedidoRepository) {
        this.rutaRepository     = rutaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository  = usuarioRepository;
        this.pedidoRepository   = pedidoRepository;
    }

    @Transactional
    public RutaResponse planificar(CrearRutaRequest request) {
        // Paso 1: Verificar vehículo
        Vehiculo vehiculo = vehiculoRepository.findById(request.idVehiculo())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehículo no encontrado: " + request.idVehiculo()));
        if (!vehiculo.getDisponible()) {
            throw new BusinessException(
                    "El vehículo " + vehiculo.getPlaca() + " no está disponible");
        }

        // Paso 2: Verificar conductor
        Usuario conductor = usuarioRepository.findById(request.idConductor())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Conductor no encontrado: " + request.idConductor()));
        if (conductor.getRol() != RolUsuario.conductor) {
            throw new BusinessException(
                    "El usuario " + conductor.getNombre() + " no tiene rol de conductor");
        }

        // Paso 3: Cargar y validar pedidos
        List<Pedido> pedidos = cargarPedidos(request.idsPedidos());

        // Paso 4: Consulta API de tráfico (placeholder — integración futura)
        log.info("[PlanificacionFacade] Consultando API de tráfico para {} paradas...",
                pedidos.size());
        // traficoAdapter.obtenerETAs(pedidos); // pendiente implementar TraficoAdapter

        // Paso 5: Ordenar paradas por ventana de entrega (orden natural por ventanaInicio)
        pedidos.sort((a, b) -> {
            if (a.getVentanaInicio() == null) return 1;
            if (b.getVentanaInicio() == null) return -1;
            return a.getVentanaInicio().compareTo(b.getVentanaInicio());
        });

        // Paso 6: Crear ruta
        Ruta ruta = Ruta.builder()
                .vehiculo(vehiculo)
                .conductor(conductor)
                .fechaRuta(request.fechaRuta())
                .estado(EstadoRuta.planificada)
                .build();
        Ruta saved = rutaRepository.save(ruta);

        // Paso 7: Asignar pedidos a la ruta con orden secuencial
        AtomicInteger orden = new AtomicInteger(1);
        pedidos.forEach(pedido -> {
            pedido.setIdRuta(saved.getIdRuta());
            pedido.setOrdenEnRuta(orden.getAndIncrement());
            pedidoRepository.save(pedido);
        });

        // Paso 8: Marcar vehículo como no disponible
        vehiculo.setDisponible(false);
        vehiculoRepository.save(vehiculo);

        // Paso 9: Notificar conductor (placeholder — integración futura async)
        log.info("[PlanificacionFacade] Notificando al conductor {} sobre ruta {}",
                conductor.getNombre(), saved.getIdRuta());
        // alertaEventPublisher.publicarRutaAsignada(saved, conductor);

        return RutaResponse.from(saved);
    }

    @Transactional
    public RutaResponse actualizarEstado(Integer idRuta, EstadoRuta nuevoEstado) {
        Ruta ruta = rutaRepository.findById(idRuta)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada: " + idRuta));

        ruta.setEstado(nuevoEstado);

        if (nuevoEstado == EstadoRuta.completada || nuevoEstado == EstadoRuta.cancelada) {
            ruta.getVehiculo().setDisponible(true);
            vehiculoRepository.save(ruta.getVehiculo());
        }
        if (nuevoEstado == EstadoRuta.en_curso) {
            ruta.setHoraInicioReal(OffsetDateTime.now());
        }
        if (nuevoEstado == EstadoRuta.completada) {
            ruta.setHoraFinReal(OffsetDateTime.now());
        }

        return RutaResponse.from(rutaRepository.save(ruta));
    }

    private List<Pedido> cargarPedidos(List<Integer> ids) {
        return ids.stream()
                .map(id -> pedidoRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Pedido no encontrado: " + id)))
                .collect(java.util.stream.Collectors.toList()); // ✅ mutable
    }
}
