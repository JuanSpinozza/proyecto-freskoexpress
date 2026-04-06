package com.freskoexpress.api.domain.pedido.dto;

import com.freskoexpress.api.domain.pedido.Cliente;
import com.freskoexpress.api.shared.enums.TipoCliente;

import java.math.BigDecimal;

public record ClienteResponse(
        Integer idCliente,
        Integer idUsuario,
        String nombreUsuario,
        String razonSocial,
        String nit,
        TipoCliente tipo,
        String direccion,
        String ciudad,
        BigDecimal coordenadaLat,
        BigDecimal coordenadaLng
) {
    public static ClienteResponse from(Cliente c) {
        return new ClienteResponse(
                c.getIdCliente(),
                c.getUsuario().getIdUsuario(),
                c.getUsuario().getNombre(),
                c.getRazonSocial(),
                c.getNit(),
                c.getTipo(),
                c.getDireccion(),
                c.getCiudad(),
                c.getCoordenadaLat(),
                c.getCoordenadaLng()
        );
    }
}