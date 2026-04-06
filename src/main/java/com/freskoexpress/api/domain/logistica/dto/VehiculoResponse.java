package com.freskoexpress.api.domain.logistica.dto;

import com.freskoexpress.api.domain.logistica.Vehiculo;
import com.freskoexpress.api.shared.enums.TipoVehiculo;

import java.math.BigDecimal;

public record VehiculoResponse(
        Integer idVehiculo,
        String placa,
        TipoVehiculo tipo,
        String marca,
        String modelo,
        BigDecimal capacidadKg,
        Integer kilometraje,
        Boolean disponible,
        String ciudad
) {
    public static VehiculoResponse from(Vehiculo v) {
        return new VehiculoResponse(
                v.getIdVehiculo(), v.getPlaca(), v.getTipo(),
                v.getMarca(), v.getModelo(), v.getCapacidadKg(),
                v.getKilometraje(), v.getDisponible(), v.getCiudad()
        );
    }
}