package com.freskoexpress.api.domain.mantenimiento.dto;

import com.freskoexpress.domain.mantenimiento.Mantenimiento;
import com.freskoexpress.shared.enums.TipoMantenimiento;

import java.time.LocalDate;

public record MantenimientoResponse(
    Integer idMantenimiento,
    Integer idVehiculo,
    String placaVehiculo,
    TipoMantenimiento tipo,
    String descripcion,
    Integer kmEnServicio,
    LocalDate fechaServicio,
    Integer proxKm,
    LocalDate proxFecha
) {
    public static MantenimientoResponse from(Mantenimiento m) {
        return new MantenimientoResponse(m.getIdMantenimiento(),
            m.getVehiculo().getIdVehiculo(), m.getVehiculo().getPlaca(),
            m.getTipo(), m.getDescripcion(), m.getKmEnServicio(),
            m.getFechaServicio(), m.getProxKm(), m.getProxFecha());
    }
}
