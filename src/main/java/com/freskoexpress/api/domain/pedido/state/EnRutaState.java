package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.api.domain.pedido.Pedido;
import com.freskoexpress.api.shared.enums.EstadoPedido;
import com.freskoexpress.api.shared.exception.InvalidStateTransitionException;
import org.springframework.stereotype.Component;

/**
 * Estado EN_RUTA: solo puede ir a ENTREGADO o FALLIDO.
 */
@Component
public class EnRutaState implements PedidoState {

    @Override
    public void aPreparacion(Pedido pedido) {
        throw new InvalidStateTransitionException("en_ruta", "preparacion");
    }

    @Override
    public void aEnRuta(Pedido pedido) {
        throw new InvalidStateTransitionException("en_ruta", "en_ruta");
    }

    @Override
    public void aEntregado(Pedido pedido) {
        pedido.setEstado(EstadoPedido.entregado);
    }

    @Override
    public void aFallido(Pedido pedido) {
        pedido.setEstado(EstadoPedido.fallido);
    }

    @Override
    public EstadoPedido getEstado() { return EstadoPedido.en_ruta; }
}
