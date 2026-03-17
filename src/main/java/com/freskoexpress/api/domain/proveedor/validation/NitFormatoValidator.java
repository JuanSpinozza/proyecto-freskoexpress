package com.freskoexpress.api.domain.proveedor.validation;

import com.freskoexpress.api.domain.proveedor.dto.CrearProveedorRequest;
import com.freskoexpress.api.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * Eslabón 1: Valida que el NIT tenga formato colombiano válido.
 * Formato esperado: dígitos con guión y dígito verificador (ej: 900234567-1)
 */
@Component
public class NitFormatoValidator extends ProveedorValidationHandler {

    private static final String NIT_REGEX = "^\\d{7,10}-\\d$";


    @Override
    protected void doValidate(CrearProveedorRequest request) {
        if (request.nit() == null || !request.nit().matches(NIT_REGEX)) {
            throw new BusinessException(
                    "Formato de NIT inválido. Use el formato: 900234567-1");
        }
    }
}
