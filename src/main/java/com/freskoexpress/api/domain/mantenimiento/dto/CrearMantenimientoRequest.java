package com.freskoexpress.api.domain.mantenimiento.dto;

import com.freskoexpress.api.shared.enums.TipoMantenimiento;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CrearMantenimientoRequest(
    @NotNull                   Integer idVehiculo,
    @NotNull                   TipoMantenimiento tipo,
    @NotBlank                  String descripcion,
    @NotNull @Min(0)           Integer kmEnServicio,
    @NotNull                   LocalDate fechaServicio,
    Integer proxKm,
    LocalDate proxFecha
) {}
