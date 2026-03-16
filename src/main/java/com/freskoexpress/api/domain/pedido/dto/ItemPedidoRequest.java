package com.freskoexpress.api.domain.pedido.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ItemPedidoRequest(
    @NotNull                      Integer idProducto,
    @NotNull @DecimalMin("0.01")  BigDecimal cantidad
) {}
