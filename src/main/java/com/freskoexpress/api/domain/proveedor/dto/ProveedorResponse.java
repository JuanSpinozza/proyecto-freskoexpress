package com.freskoexpress.api.domain.proveedor.dto;

import com.freskoexpress.domain.proveedor.Proveedor;
import com.freskoexpress.shared.enums.EstadoProveedor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProveedorResponse(
    Integer idProveedor,
    String nit,
    String razonSocial,
    String contactoCorreo,
    String contactoTelefono,
    BigDecimal capacidadSemanal,
    EstadoProveedor estado,
    String razonRechazo,
    OffsetDateTime fechaRegistro
) {
    public static ProveedorResponse from(Proveedor p) {
        return new ProveedorResponse(
            p.getIdProveedor(), p.getNit(), p.getRazonSocial(),
            p.getContactoCorreo(), p.getContactoTelefono(), p.getCapacidadSemanal(),
            p.getEstado(), p.getRazonRechazo(), p.getFechaRegistro());
    }
}
