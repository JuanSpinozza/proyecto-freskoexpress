package com.freskoexpress.api.domain.inventario.dto;

import com.freskoexpress.api.domain.inventario.Producto;
import com.freskoexpress.api.shared.enums.CategoriaProducto;

import java.math.BigDecimal;

public record ProductoResponse(
    Integer idProducto,
    Integer idProveedor,
    String nombre,
    CategoriaProducto categoria,
    String unidadMedida,
    BigDecimal tempMinC,
    BigDecimal tempMaxC,
    BigDecimal stockMinimo,
    BigDecimal precioUnitario,
    Boolean activo
) {
    public static ProductoResponse from(Producto p) {
        return new ProductoResponse(p.getIdProducto(),
            p.getProveedor().getIdProveedor(), p.getNombre(), p.getCategoria(),
            p.getUnidadMedida(), p.getTempMinC(), p.getTempMaxC(),
            p.getStockMinimo(), p.getPrecioUnitario(), p.getActivo());
    }
}
