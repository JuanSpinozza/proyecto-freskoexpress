package com.freskoexpress.api.domain.proveedor.validation;

import com.freskoexpress.api.domain.proveedor.dto.CrearProveedorRequest;
import com.freskoexpress.api.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * Eslabón 4: Valida formato de correo y teléfono de contacto.
 */
@Component
public class ContactoValidator extends ProveedorValidationHandler {

    private static final String EMAIL_REGEX = "^[\\w.+\\-]+@[\\w\\-]+\\.[\\w.]+$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{7,15}$";

    @Override
    protected void doValidate(CrearProveedorRequest request) {
        if (request.contactoCorreo() == null
                || !request.contactoCorreo().matches(EMAIL_REGEX)) {
            throw new BusinessException("El correo de contacto no tiene un formato válido");
        }
        if (request.contactoTelefono() == null
                || !request.contactoTelefono().matches(PHONE_REGEX)) {
            throw new BusinessException("El teléfono de contacto no tiene un formato válido");
        }
    }
}
