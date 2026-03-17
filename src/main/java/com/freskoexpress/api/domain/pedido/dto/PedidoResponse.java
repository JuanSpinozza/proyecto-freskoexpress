package com.freskoexpress.api.domain.pedido.dto;

import com.freskoexpress.api.domain.pedido.Pedido;
import com.freskoexpress.api.shared.enums.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record PedidoResponse(
    Integer idPedido,
    Integer idCliente,
    String razonSocialCliente,
    EstadoPedido estado,
    OffsetDateTime fechaPedido,
    LocalDate fechaEntregaReq,
    BigDecimal total
) {
    public static PedidoResponse from(Pedido p) {
        return new PedidoResponse(p.getIdPedido(),
            p.getCliente().getIdCliente(), p.getCliente().getRazonSocial(),
            p.getEstado(), p.getFechaPedido(), p.getFechaEntregaReq(), p.getTotal());
    }
}
