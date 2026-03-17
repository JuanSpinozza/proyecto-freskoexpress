package com.freskoexpress.api.domain.inventario.dto;

import com.freskoexpress.api.shared.enums.CategoriaProducto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CrearProductoRequest(
    @NotNull                      Integer idProveedor,
    @NotBlank @Size(max = 150)    String nombre,
    @NotNull CategoriaProducto categoria,
    @NotBlank @Size(max = 20)     String unidadMedida,
    BigDecimal tempMinC,
    BigDecimal tempMaxC,
    @NotNull @DecimalMin("0")     BigDecimal stockMinimo,
    @NotNull @DecimalMin("0")     BigDecimal precioUnitario
) {}
