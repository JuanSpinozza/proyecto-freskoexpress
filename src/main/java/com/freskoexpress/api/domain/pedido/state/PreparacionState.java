package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.api.domain.pedido.Pedido;
import com.freskoexpress.api.shared.enums.EstadoPedido;
import com.freskoexpress.api.shared.exception.InvalidStateTransitionException;
import org.springframework.stereotype.Component;

/**
 * Estado PREPARACION: solo puede ir a EN_RUTA o FALLIDO.
 */
@Component
public class PreparacionState implements PedidoState {

    @Override
    public void aPreparacion(Pedido pedido) {
        throw new InvalidStateTransitionException("preparacion", "preparacion");
    }

    @Override
    public void aEnRuta(Pedido pedido) {
        pedido.setEstado(EstadoPedido.en_ruta);
    }

    @Override
    public void aEntregado(Pedido pedido) {
        throw new InvalidStateTransitionException("preparacion", "entregado");
    }

    @Override
    public void aFallido(Pedido pedido) {
        pedido.setEstado(EstadoPedido.fallido);
    }

    @Override
    public EstadoPedido getEstado() { return EstadoPedido.preparacion; }
}
