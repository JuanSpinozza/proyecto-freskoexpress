package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.shared.enums.EstadoPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resuelve qué objeto de estado corresponde a un EstadoPedido.
 * Inyectado en PedidoService para obtener el estado actual del pedido
 * sin ningún switch o if.
 */
@Component
@RequiredArgsConstructor
public class PedidoStateResolver {

    private final List<PedidoState> states;

    public PedidoState resolve(EstadoPedido estadoPedido) {
        return states.stream()
            .filter(s -> s.getEstado() == estadoPedido)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Estado no reconocido: " + estadoPedido));
    }
}
