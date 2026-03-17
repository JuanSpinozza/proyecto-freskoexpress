package com.freskoexpress.api.domain.proveedor.validation;

import com.freskoexpress.api.domain.proveedor.dto.CrearProveedorRequest;
import org.springframework.stereotype.Component;

/**
 * Ensambla y expone la cadena de validación completa.
 *
 * El ProveedorService solo conoce este componente,
 * no sabe nada sobre los eslabones individuales.
 *
 * Orden de ejecución: Formato NIT → NIT único → Capacidad → Contacto
 */
@Component
public class ProveedorValidationChain {

    private final NitFormatoValidator nitFormato;
    private final NitUnicoValidator   nitUnico;
    private final CapacidadValidator  capacidad;
    private final ContactoValidator   contacto;

    public ProveedorValidationChain(NitFormatoValidator nitFormato,
                                    NitUnicoValidator nitUnico,
                                    CapacidadValidator capacidad,
                                    ContactoValidator contacto) {
        this.nitFormato = nitFormato;
        this.nitUnico   = nitUnico;
        this.capacidad  = capacidad;
        this.contacto   = contacto;
    }

    public void validate(CrearProveedorRequest request) {
        nitFormato
                .setNext(nitUnico)
                .setNext(capacidad)
                .setNext(contacto);

        nitFormato.validate(request);
    }
}