package com.freskoexpress.api.domain.pedido;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DetallePedidoId implements Serializable {

    private Integer idPedido;
    private Integer idLote;

    // ── Constructores ─────────────────────────────────────────────────
    public DetallePedidoId() {}   // requerido por JPA

    public DetallePedidoId(Integer idPedido, Integer idLote) {
        this.idPedido = idPedido;
        this.idLote   = idLote;
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer getIdPedido() { return idPedido; }
    public Integer getIdLote()   { return idLote; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }
    public void setIdLote(Integer idLote)     { this.idLote = idLote; }

    // ── equals y hashCode ─────────────────────────────────────────────
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetallePedidoId that = (DetallePedidoId) o;
        return Objects.equals(idPedido, that.idPedido)
                && Objects.equals(idLote,   that.idLote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idLote);
    }
}