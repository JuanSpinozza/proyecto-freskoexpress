package com.freskoexpress.api.domain.facturacion.strategy;

import com.freskoexpress.api.domain.facturacion.dto.RegistrarPagoRequest;
import com.freskoexpress.api.shared.enums.MetodoPago;
import org.springframework.stereotype.Component;

/**
 * Estrategia: Tarjeta de crédito.
 * Delega al Gateway de Pago externo (PayU / Stripe).
 * En producción aquí iría la llamada al PagoAdapter → Gateway externo.
 */
@Component
public class TarjetaPagoStrategy implements PagoStrategy {

    @Override
    public String procesar(RegistrarPagoRequest request) {
        // TODO: PagoAdapter → Gateway externo → retorna transactionId
        return "TXN-" + System.currentTimeMillis();
    }

    @Override
    public boolean aplica(MetodoPago metodo) {
        return metodo == MetodoPago.tarjeta;
    }
}
