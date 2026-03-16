package com.freskoexpress.api.domain.proveedor.validation;

import com.freskoexpress.domain.proveedor.ProveedorRepository;
import com.freskoexpress.domain.proveedor.dto.CrearProveedorRequest;
import com.freskoexpress.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Eslabón 2: Valida que el NIT no esté ya registrado en el sistema.
 */
@Component
@RequiredArgsConstructor
public class NitUnicoValidator extends ProveedorValidationHandler {

    private final ProveedorRepository proveedorRepository;

    @Override
    protected void doValidate(CrearProveedorRequest request) {
        if (proveedorRepository.existsByNit(request.nit())) {
            throw new BusinessException(
                "Ya existe un proveedor registrado con NIT: " + request.nit());
        }
    }
}
