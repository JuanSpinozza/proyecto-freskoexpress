package com.freskoexpress.api.domain.pedido.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CrearPedidoRequest(
    @NotNull Integer idCliente,
    @NotNull
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "2026-04-07")
    LocalDate fechaEntregaReq,

    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "09:00:00")
    LocalTime ventanaInicio,

    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "12:00:00")
    LocalTime ventanaFin,
    @NotEmpty List<ItemPedidoRequest> items
) {}
