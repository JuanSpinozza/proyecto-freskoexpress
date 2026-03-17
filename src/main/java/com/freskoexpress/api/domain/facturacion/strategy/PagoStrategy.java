package com.freskoexpress.api.domain.facturacion.strategy;

import com.freskoexpress.api.domain.facturacion.dto.RegistrarPagoRequest;
import com.freskoexpress.api.shared.enums.MetodoPago;

/**
 * Strategy Pattern — Procesamiento de pagos.
 *
 * Permite intercambiar el algoritmo de validación y procesamiento
 * según el método de pago, sin que FacturaService sepa cuál se usa.
 *
 * Para agregar PSE u otro método: crear nueva clase que implemente
 * esta interfaz. FacturaService no se modifica en absoluto.
 */
public interface PagoStrategy {
    /**
     * Valida y procesa el pago.
     * @return referencia de pago (transactionId, referencia bancaria, etc.)
     */
    String procesar(RegistrarPagoRequest request);

    /** Determina si esta estrategia aplica para el método dado. */
    boolean aplica(MetodoPago metodo);
}
