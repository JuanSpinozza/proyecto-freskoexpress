package com.freskoexpress.api.domain.proveedor.validation;

import com.freskoexpress.api.domain.proveedor.dto.CrearProveedorRequest;
import com.freskoexpress.api.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Eslabón 3: Valida que la capacidad semanal sea un valor positivo razonable.
 * La BD ya tiene CHECK > 0, pero esta validación da un mensaje claro antes de llegar a la BD.
 */
@Component
public class CapacidadValidator extends ProveedorValidationHandler {

    private static final BigDecimal MAX_CAPACIDAD = new BigDecimal("999999.99");

    @Override
    protected void doValidate(CrearProveedorRequest request) {
        if (request.capacidadSemanal() == null
                || request.capacidadSemanal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("La capacidad semanal debe ser mayor a 0");
        }
        if (request.capacidadSemanal().compareTo(MAX_CAPACIDAD) > 0) {
            throw new BusinessException("La capacidad semanal supera el máximo permitido");
        }
    }
}
