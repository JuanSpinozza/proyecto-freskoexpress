package com.freskoexpress.api.domain.pedido.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CrearPedidoRequest(
    @NotNull Integer idCliente,
    @NotNull LocalDate fechaEntregaReq,
    LocalTime ventanaInicio,
    LocalTime ventanaFin,
    @NotEmpty List<ItemPedidoRequest> items
) {}
