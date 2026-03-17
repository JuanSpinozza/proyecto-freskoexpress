package com.freskoexpress.api.domain.facturacion.strategy;

import com.freskoexpress.api.domain.facturacion.dto.RegistrarPagoRequest;
import com.freskoexpress.api.shared.enums.MetodoPago;
import com.freskoexpress.api.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Contexto del Strategy Pattern.
 * Spring inyecta automáticamente todas las implementaciones de PagoStrategy.
 * Selecciona la estrategia correcta según el MetodoPago recibido.
 *
 * FacturaService solo depende de PagoContext, no de ninguna estrategia concreta.
 */
@Component
public class PagoContext {

    private final List<PagoStrategy> strategies;

    public PagoContext(List<PagoStrategy> strategies) {
        this.strategies = strategies;
    }

    public String ejecutar(MetodoPago metodo, RegistrarPagoRequest request) {
        return strategies.stream()
                .filter(s -> s.aplica(metodo))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Método de pago no soportado: " + metodo))
                .procesar(request);
    }
}
