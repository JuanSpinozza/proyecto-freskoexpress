package com.freskoexpress.api.domain.facturacion.strategy;

import com.freskoexpress.domain.facturacion.dto.RegistrarPagoRequest;
import com.freskoexpress.shared.enums.MetodoPago;
import org.springframework.stereotype.Component;

/**
 * Estrategia: Pago contra entrega.
 * El conductor registra el pago físico desde la PWA móvil.
 * No requiere integración externa; solo genera una referencia interna.
 */
@Component
public class ContraEntregaPagoStrategy implements PagoStrategy {

    @Override
    public String procesar(RegistrarPagoRequest request) {
        return "CASH-" + System.currentTimeMillis();
    }

    @Override
    public boolean aplica(MetodoPago metodo) {
        return metodo == MetodoPago.contra_entrega;
    }
}
