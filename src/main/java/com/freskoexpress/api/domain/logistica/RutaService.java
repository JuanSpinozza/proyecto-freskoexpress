package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.api.domain.logistica.dto.*;
import com.freskoexpress.api.domain.logistica.facade.PlanificacionFacade;
import com.freskoexpress.api.shared.enums.EstadoRuta;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * RutaService delega el proceso complejo al PlanificacionFacade.
 * Aquí solo viven operaciones simples de consulta.
 */
@Service
public class RutaService {

    private final PlanificacionFacade planificacionFacade;
    private final RutaRepository      rutaRepository;

    public RutaService(PlanificacionFacade planificacionFacade,
                       RutaRepository rutaRepository) {
        this.planificacionFacade = planificacionFacade;
        this.rutaRepository      = rutaRepository;
    }

    public RutaResponse crearRuta(CrearRutaRequest request) {
        return planificacionFacade.planificar(request);
    }

    public RutaResponse actualizarEstado(Integer idRuta, EstadoRuta estado) {
        return planificacionFacade.actualizarEstado(idRuta, estado);
    }

    public List<RutaResponse> listarPorFecha(LocalDate fecha) {
        return rutaRepository.findByFechaRuta(fecha)
                .stream().map(RutaResponse::from).toList();
    }

    public List<RutaResponse> listarPorConductor(Integer idConductor) {
        return rutaRepository.findByConductorIdUsuario(idConductor)
                .stream().map(RutaResponse::from).toList();
    }
}