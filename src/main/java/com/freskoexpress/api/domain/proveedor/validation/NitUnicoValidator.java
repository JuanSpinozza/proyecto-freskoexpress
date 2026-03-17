package com.freskoexpress.api.domain.proveedor.validation;

import com.freskoexpress.api.domain.proveedor.ProveedorRepository;
import com.freskoexpress.api.domain.proveedor.dto.CrearProveedorRequest;
import com.freskoexpress.api.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * Eslabón 2: Valida que el NIT no esté ya registrado en el sistema.
 */
@Component
public class NitUnicoValidator extends ProveedorValidationHandler {

    private final ProveedorRepository proveedorRepository;

    public NitUnicoValidator(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    protected void doValidate(CrearProveedorRequest request) {
        if (proveedorRepository.existsByNit(request.nit())) {
            throw new BusinessException(
                    "Ya existe un proveedor registrado con NIT: " + request.nit());
        }
    }
}