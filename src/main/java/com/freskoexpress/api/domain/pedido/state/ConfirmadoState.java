package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.domain.pedido.Pedido;
import com.freskoexpress.shared.enums.EstadoPedido;
import com.freskoexpress.shared.exception.InvalidStateTransitionException;
import org.springframework.stereotype.Component;

/**
 * Estado CONFIRMADO: solo puede ir a PREPARACION o FALLIDO.
 */
@Component
public class ConfirmadoState implements PedidoState {

    @Override
    public void aPreparacion(Pedido pedido) {
        pedido.setEstado(EstadoPedido.preparacion);
    }

    @Override
    public void aEnRuta(Pedido pedido) {
        throw new InvalidStateTransitionException("confirmado", "en_ruta");
    }

    @Override
    public void aEntregado(Pedido pedido) {
        throw new InvalidStateTransitionException("confirmado", "entregado");
    }

    @Override
    public void aFallido(Pedido pedido) {
        pedido.setEstado(EstadoPedido.fallido);
    }

    @Override
    public EstadoPedido getEstado() { return EstadoPedido.confirmado; }
}
