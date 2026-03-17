package com.freskoexpress.api.domain.inventario.dto;

import com.freskoexpress.api.domain.inventario.Lote;
import com.freskoexpress.api.shared.enums.EstadoLote;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoteResponse(
    Integer idLote,
    Integer idProducto,
    String nombreProducto,
    Integer idProveedor,
    String numeroLote,
    BigDecimal cantidadActual,
    LocalDate fechaIngreso,
    LocalDate fechaVencimiento,
    EstadoLote estado
) {
    public static LoteResponse from(Lote l) {
        return new LoteResponse(l.getIdLote(),
            l.getProducto().getIdProducto(), l.getProducto().getNombre(),
            l.getProveedor().getIdProveedor(), l.getNumeroLote(),
            l.getCantidadActual(), l.getFechaIngreso(),
            l.getFechaVencimiento(), l.getEstado());
    }
}
