package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.api.domain.pedido.Pedido;
import com.freskoexpress.api.shared.enums.EstadoPedido;
import com.freskoexpress.api.shared.exception.InvalidStateTransitionException;
import org.springframework.stereotype.Component;

/**
 * Estado ENTREGADO: estado terminal, no permite transiciones.
 */
@Component
public class EntregadoState implements PedidoState {

    @Override public void aPreparacion(Pedido p) { reject("entregado", "preparacion"); }
    @Override public void aEnRuta(Pedido p)      { reject("entregado", "en_ruta"); }
    @Override public void aEntregado(Pedido p)   { reject("entregado", "entregado"); }
    @Override public void aFallido(Pedido p)     { reject("entregado", "fallido"); }

    @Override
    public EstadoPedido getEstado() { return EstadoPedido.entregado; }

    private void reject(String from, String to) {
        throw new InvalidStateTransitionException(from, to + " (ENTREGADO es estado terminal)");
    }
}
