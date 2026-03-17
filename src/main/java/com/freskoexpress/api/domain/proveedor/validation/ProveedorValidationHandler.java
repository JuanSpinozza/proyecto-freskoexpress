package com.freskoexpress.api.domain.proveedor.validation;

import com.freskoexpress.api.domain.proveedor.dto.CrearProveedorRequest;

/**
 * Chain of Responsibility — Validación de proveedores.
 *
 * Cada eslabón valida una regla de negocio específica.
 * Si la validación falla lanza BusinessException.
 * Si pasa, delega al siguiente eslabón.
 *
 * Agregar una nueva regla = nueva clase que extiende esta interfaz.
 * No se modifica ninguna clase existente (Open/Closed Principle).
 */
public abstract class ProveedorValidationHandler {

    private ProveedorValidationHandler next;

    public ProveedorValidationHandler setNext(ProveedorValidationHandler next) {
        this.next = next;
        return next;
    }

    public final void validate(CrearProveedorRequest request) {
        doValidate(request);
        if (next != null) next.validate(request);
    }

    protected abstract void doValidate(CrearProveedorRequest request);
}
