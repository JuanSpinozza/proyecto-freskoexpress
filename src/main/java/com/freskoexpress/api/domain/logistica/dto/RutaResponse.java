package com.freskoexpress.api.domain.logistica.dto;

import com.freskoexpress.api.domain.logistica.Ruta;
import com.freskoexpress.api.shared.enums.EstadoRuta;

import java.time.LocalDate;

public record RutaResponse(
    Integer idRuta,
    Integer idVehiculo,
    String placaVehiculo,
    Integer idConductor,
    String nombreConductor,
    LocalDate fechaRuta,
    EstadoRuta estado
) {
    public static RutaResponse from(Ruta r) {
        return new RutaResponse(r.getIdRuta(),
            r.getVehiculo().getIdVehiculo(), r.getVehiculo().getPlaca(),
            r.getConductor().getIdUsuario(), r.getConductor().getNombre(),
            r.getFechaRuta(), r.getEstado());
    }
}
