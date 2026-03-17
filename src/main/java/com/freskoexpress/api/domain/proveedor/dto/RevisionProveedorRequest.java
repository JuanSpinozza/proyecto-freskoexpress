package com.freskoexpress.api.domain.proveedor.dto;

import com.freskoexpress.api.shared.enums.EstadoProveedor;
import jakarta.validation.constraints.NotNull;

public record RevisionProveedorRequest(
    @NotNull EstadoProveedor nuevoEstado,
    String razonRechazo    // obligatorio si nuevoEstado = rechazado
) {}
