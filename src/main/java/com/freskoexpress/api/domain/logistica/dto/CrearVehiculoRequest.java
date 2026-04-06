package com.freskoexpress.api.domain.logistica.dto;

import com.freskoexpress.api.shared.enums.TipoVehiculo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CrearVehiculoRequest(
        @NotBlank @Size(max = 15)  String placa,
        @NotNull                   TipoVehiculo tipo,
        @NotBlank @Size(max = 50)  String marca,
        @NotBlank @Size(max = 50)  String modelo,
        @NotNull @DecimalMin("0.01") BigDecimal capacidadKg,
        @NotBlank @Size(max = 100) String ciudad
) {}