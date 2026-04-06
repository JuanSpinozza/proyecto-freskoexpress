package com.freskoexpress.api.domain.pedido.dto;

import com.freskoexpress.api.shared.enums.TipoCliente;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CrearClienteRequest(
        @NotNull                      Integer idUsuario,
        @NotBlank @Size(max = 150)    String razonSocial,
        @NotBlank @Size(max = 20)     String nit,
        @NotNull                      TipoCliente tipo,
        @NotBlank @Size(max = 300)    String direccion,
        @NotBlank @Size(max = 100)    String ciudad,
        BigDecimal coordenadaLat,
        BigDecimal coordenadaLng
) {}