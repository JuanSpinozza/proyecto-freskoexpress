package com.freskoexpress.api.domain.proveedor.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CrearProveedorRequest(
    @NotBlank @Size(max = 20)  String nit,
    @NotBlank @Size(max = 150) String razonSocial,
    @NotBlank                  String contactoCorreo,
    @NotBlank @Size(max = 20)  String contactoTelefono,
    @NotNull                   BigDecimal capacidadSemanal
) {}
