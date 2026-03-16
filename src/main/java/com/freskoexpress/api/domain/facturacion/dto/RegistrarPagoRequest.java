package com.freskoexpress.api.domain.facturacion.dto;

import com.freskoexpress.shared.enums.MetodoPago;
import jakarta.validation.constraints.NotNull;

public record RegistrarPagoRequest(
    @NotNull MetodoPago metodoPago,
    String referenciaPago
) {}
