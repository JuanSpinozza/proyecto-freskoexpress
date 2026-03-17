package com.freskoexpress.api.domain.facturacion;

import com.freskoexpress.api.domain.pedido.Pedido;
import com.freskoexpress.api.shared.enums.EstadoFactura;
import com.freskoexpress.api.shared.enums.MetodoPago;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;

    @Column(name = "numero_factura", nullable = false, unique = true, length = 30)
    private String numeroFactura;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoFactura estado = EstadoFactura.pendiente;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "referencia_pago", length = 100)
    private String referenciaPago;

    @Column(name = "fecha_emision", nullable = false, updatable = false)
    private OffsetDateTime fechaEmision;

    @Column(name = "fecha_pago")
    private OffsetDateTime fechaPago;

    // ── Constructores ─────────────────────────────────────────────────
    protected Factura() {}   // requerido por JPA

    private Factura(Builder builder) {
        this.pedido          = builder.pedido;
        this.numeroFactura   = builder.numeroFactura;
        this.total           = builder.total;
        this.estado          = builder.estado;
        this.metodoPago      = builder.metodoPago;
        this.referenciaPago  = builder.referenciaPago;
        this.fechaPago       = builder.fechaPago;
        // fechaEmision no va en el Builder — la asigna @PrePersist
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaEmision == null) this.fechaEmision = OffsetDateTime.now();
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Pedido        pedido;
        private String        numeroFactura;
        private BigDecimal    total;
        private EstadoFactura estado         = EstadoFactura.pendiente;
        private MetodoPago    metodoPago;
        private String        referenciaPago;
        private OffsetDateTime fechaPago;

        public Builder pedido(Pedido pedido) {
            this.pedido = pedido;
            return this;
        }

        public Builder numeroFactura(String numeroFactura) {
            this.numeroFactura = numeroFactura;
            return this;
        }

        public Builder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Builder estado(EstadoFactura estado) {
            this.estado = estado;
            return this;
        }

        public Builder metodoPago(MetodoPago metodoPago) {
            this.metodoPago = metodoPago;
            return this;
        }

        public Builder referenciaPago(String referenciaPago) {
            this.referenciaPago = referenciaPago;
            return this;
        }

        public Builder fechaPago(OffsetDateTime fechaPago) {
            this.fechaPago = fechaPago;
            return this;
        }

        public Factura build() {
            if (pedido == null) {
                throw new IllegalStateException("El pedido es obligatorio");
            }
            if (numeroFactura == null || numeroFactura.isBlank()) {
                throw new IllegalStateException("El número de factura es obligatorio");
            }
            if (total == null || total.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("El total debe ser mayor o igual a 0");
            }
            if (estado == EstadoFactura.pagada && metodoPago == null) {
                throw new IllegalStateException(
                        "El método de pago es obligatorio cuando el estado es pagada");
            }
            if (estado == EstadoFactura.pagada && fechaPago == null) {
                throw new IllegalStateException(
                        "La fecha de pago es obligatoria cuando el estado es pagada");
            }
            return new Factura(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer        getIdFactura()     { return idFactura; }
    public Pedido         getPedido()        { return pedido; }
    public String         getNumeroFactura() { return numeroFactura; }
    public BigDecimal     getTotal()         { return total; }
    public EstadoFactura  getEstado()        { return estado; }
    public MetodoPago     getMetodoPago()    { return metodoPago; }
    public String         getReferenciaPago(){ return referenciaPago; }
    public OffsetDateTime getFechaEmision()  { return fechaEmision; }
    public OffsetDateTime getFechaPago()     { return fechaPago; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdFactura(Integer idFactura)          { this.idFactura = idFactura; }
    public void setPedido(Pedido pedido)                 { this.pedido = pedido; }
    public void setNumeroFactura(String numeroFactura)   { this.numeroFactura = numeroFactura; }
    public void setTotal(BigDecimal total)               { this.total = total; }
    public void setEstado(EstadoFactura estado)          { this.estado = estado; }
    public void setMetodoPago(MetodoPago metodoPago)     { this.metodoPago = metodoPago; }
    public void setReferenciaPago(String referenciaPago) { this.referenciaPago = referenciaPago; }
    public void setFechaEmision(OffsetDateTime fecha)    { this.fechaEmision = fecha; }
    public void setFechaPago(OffsetDateTime fechaPago)   { this.fechaPago = fechaPago; }
}
