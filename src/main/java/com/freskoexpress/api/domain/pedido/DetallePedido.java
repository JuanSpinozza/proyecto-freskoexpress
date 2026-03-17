package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.api.domain.inventario.Lote;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @EmbeddedId
    private DetallePedidoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idLote")
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // GENERATED ALWAYS en BD — no enviamos en INSERT
    @Column(precision = 12, scale = 2, insertable = false, updatable = false)
    private BigDecimal subtotal;

    @Column(name = "lat_entrega", precision = 10, scale = 7)
    private BigDecimal latEntrega;

    @Column(name = "lng_entrega", precision = 10, scale = 7)
    private BigDecimal lngEntrega;

    // ── Constructores ─────────────────────────────────────────────────
    protected DetallePedido() {}   // requerido por JPA

    private DetallePedido(Builder builder) {
        this.id             = builder.id;
        this.pedido         = builder.pedido;
        this.lote           = builder.lote;
        this.cantidad       = builder.cantidad;
        this.precioUnitario = builder.precioUnitario;
        this.latEntrega     = builder.latEntrega;
        this.lngEntrega     = builder.lngEntrega;
        // subtotal no se asigna — es GENERATED ALWAYS en la BD
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private DetallePedidoId id;
        private Pedido          pedido;
        private Lote            lote;
        private BigDecimal      cantidad;
        private BigDecimal      precioUnitario;
        private BigDecimal      latEntrega;
        private BigDecimal      lngEntrega;

        public Builder id(DetallePedidoId id) {
            this.id = id;
            return this;
        }

        public Builder pedido(Pedido pedido) {
            this.pedido = pedido;
            return this;
        }

        public Builder lote(Lote lote) {
            this.lote = lote;
            return this;
        }

        public Builder cantidad(BigDecimal cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public Builder precioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
            return this;
        }

        public Builder latEntrega(BigDecimal latEntrega) {
            this.latEntrega = latEntrega;
            return this;
        }

        public Builder lngEntrega(BigDecimal lngEntrega) {
            this.lngEntrega = lngEntrega;
            return this;
        }

        public DetallePedido build() {
            if (pedido == null) {
                throw new IllegalStateException("El pedido es obligatorio");
            }
            if (lote == null) {
                throw new IllegalStateException("El lote es obligatorio");
            }
            if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalStateException("La cantidad debe ser mayor a 0");
            }
            if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException(
                        "El precio unitario debe ser mayor o igual a 0");
            }
            if (latEntrega != null && lngEntrega == null
                    || latEntrega == null && lngEntrega != null) {
                throw new IllegalStateException(
                        "lat_entrega y lng_entrega deben proporcionarse juntas o ninguna");
            }
            return new DetallePedido(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public DetallePedidoId getId()             { return id; }
    public Pedido          getPedido()         { return pedido; }
    public Lote            getLote()           { return lote; }
    public BigDecimal      getCantidad()       { return cantidad; }
    public BigDecimal      getPrecioUnitario() { return precioUnitario; }
    public BigDecimal      getSubtotal()       { return subtotal; }
    public BigDecimal      getLatEntrega()     { return latEntrega; }
    public BigDecimal      getLngEntrega()     { return lngEntrega; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setId(DetallePedidoId id)                  { this.id = id; }
    public void setPedido(Pedido pedido)                   { this.pedido = pedido; }
    public void setLote(Lote lote)                         { this.lote = lote; }
    public void setCantidad(BigDecimal cantidad)           { this.cantidad = cantidad; }
    public void setPrecioUnitario(BigDecimal precioUnitario){ this.precioUnitario = precioUnitario; }
    public void setLatEntrega(BigDecimal latEntrega)       { this.latEntrega = latEntrega; }
    public void setLngEntrega(BigDecimal lngEntrega)       { this.lngEntrega = lngEntrega; }
    // subtotal no tiene setter — es GENERATED ALWAYS, solo lectura desde la BD
}