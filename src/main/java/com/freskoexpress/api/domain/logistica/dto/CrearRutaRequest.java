package com.freskoexpress.api.domain.logistica.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CrearRutaRequest(
    @NotNull Integer idVehiculo,
    @NotNull Integer idConductor,
    @NotNull LocalDate fechaRuta,
    @NotNull List<Integer> idsPedidos
) {}
