package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.api.shared.enums.EstadoPedido;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resuelve qué objeto de estado corresponde a un EstadoPedido.
 * Inyectado en PedidoService para obtener el estado actual del pedido
 * sin ningún switch o if.
 */
@Component
public class PedidoStateResolver {

    private final List<PedidoState> states;

    public PedidoStateResolver(List<PedidoState> states) {
        this.states = states;
    }

    public PedidoState resolve(EstadoPedido estadoPedido) {
        return states.stream()
                .filter(s -> s.getEstado() == estadoPedido)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Estado no reconocido: " + estadoPedido));
    }
}