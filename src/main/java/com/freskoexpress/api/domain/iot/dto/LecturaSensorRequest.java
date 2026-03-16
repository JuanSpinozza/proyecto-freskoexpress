package com.freskoexpress.api.domain.iot.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record LecturaSensorRequest(
    @NotNull                           Integer idVehiculo,
    Integer idRuta,
    @NotNull                           BigDecimal temperaturaC,
    @NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal humedadPct
) {}
