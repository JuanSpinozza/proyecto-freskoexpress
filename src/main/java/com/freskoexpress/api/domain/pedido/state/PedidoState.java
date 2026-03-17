package com.freskoexpress.api.domain.pedido.state;

import com.freskoexpress.api.domain.pedido.Pedido;
import com.freskoexpress.api.shared.enums.EstadoPedido;

/**
 * State Pattern — Ciclo de vida del Pedido.
 *
 * Cada estado concreto define qué transiciones son válidas desde él.
 * PedidoService no contiene ningún if/switch sobre EstadoPedido;
 * en su lugar le pide al estado actual que ejecute la transición.
 *
 * Beneficio: agregar un nuevo estado solo requiere una nueva clase.
 * El servicio no se modifica (Open/Closed Principle).
 */
public interface PedidoState {
    void aPreparacion(Pedido pedido);
    void aEnRuta(Pedido pedido);
    void aEntregado(Pedido pedido);
    void aFallido(Pedido pedido);
    EstadoPedido getEstado();
}
