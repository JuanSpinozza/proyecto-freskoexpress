package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.domain.pedido.Pedido;
import com.freskoexpress.shared.enums.EstadoPedido;
import com.freskoexpress.shared.exception.InvalidStateTransitionException;
import org.springframework.stereotype.Component;

/**
 * Estado FALLIDO: estado terminal, no permite transiciones.
 */
@Component
public class FallidoState implements PedidoState {

    @Override public void aPreparacion(Pedido p) { reject(); }
    @Override public void aEnRuta(Pedido p)      { reject(); }
    @Override public void aEntregado(Pedido p)   { reject(); }
    @Override public void aFallido(Pedido p)     { reject(); }

    @Override
    public EstadoPedido getEstado() { return EstadoPedido.fallido; }

    private void reject() {
        throw new InvalidStateTransitionException("fallido", "cualquier estado (FALLIDO es terminal)");
    }
}
