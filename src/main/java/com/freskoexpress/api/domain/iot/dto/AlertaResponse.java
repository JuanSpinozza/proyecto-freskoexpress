package com.freskoexpress.api.domain.iot.dto;

import com.freskoexpress.domain.iot.Alerta;
import com.freskoexpress.shared.enums.SeveridadAlerta;
import com.freskoexpress.shared.enums.TipoAlerta;

import java.time.OffsetDateTime;

public record AlertaResponse(
    Integer idAlerta,
    TipoAlerta tipo,
    SeveridadAlerta severidad,
    String mensaje,
    Boolean reconocida,
    OffsetDateTime fechaAlerta,
    Integer idVehiculo,
    Integer idProducto
) {
    public static AlertaResponse from(Alerta a) {
        return new AlertaResponse(a.getIdAlerta(), a.getTipo(), a.getSeveridad(),
            a.getMensaje(), a.getReconocida(), a.getFechaAlerta(),
            a.getVehiculo()  != null ? a.getVehiculo().getIdVehiculo()   : null,
            a.getProducto()  != null ? a.getProducto().getIdProducto()   : null);
    }
}
