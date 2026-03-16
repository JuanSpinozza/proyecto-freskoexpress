package com.freskoexpress.api.domain.inventario.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CrearLoteRequest(
    @NotNull                      Integer idProducto,
    @NotNull                      Integer idProveedor,
    @NotBlank @Size(max = 50)     String numeroLote,
    @NotNull @DecimalMin("0.01")  BigDecimal cantidadActual,
    @NotNull                      LocalDate fechaIngreso,
    @NotNull                      LocalDate fechaVencimiento
) {}
