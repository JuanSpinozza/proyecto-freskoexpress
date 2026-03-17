package com.freskoexpress.api.domain.facturacion.dto;

import com.freskoexpress.api.domain.facturacion.Factura;
import com.freskoexpress.api.shared.enums.EstadoFactura;
import com.freskoexpress.api.shared.enums.MetodoPago;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record FacturaResponse(
    Integer idFactura,
    Integer idPedido,
    String numeroFactura,
    BigDecimal total,
    EstadoFactura estado,
    MetodoPago metodoPago,
    String referenciaPago,
    OffsetDateTime fechaEmision,
    OffsetDateTime fechaPago
) {
    public static FacturaResponse from(Factura f) {
        return new FacturaResponse(f.getIdFactura(),
            f.getPedido().getIdPedido(), f.getNumeroFactura(),
            f.getTotal(), f.getEstado(), f.getMetodoPago(),
            f.getReferenciaPago(), f.getFechaEmision(), f.getFechaPago());
    }
}
