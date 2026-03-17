package com.freskoexpress.api.domain.inventario;

import com.freskoexpress.api.domain.proveedor.Proveedor;
import com.freskoexpress.api.shared.enums.EstadoLote;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "numero_lote", nullable = false, length = 50)
    private String numeroLote;

    @Column(name = "cantidad_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadActual;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLote estado = EstadoLote.activo;

    // ── Constructores ─────────────────────────────────────────────────
    protected Lote() {}   // requerido por JPA

    private Lote(Builder builder) {
        this.producto         = builder.producto;
        this.proveedor        = builder.proveedor;
        this.numeroLote       = builder.numeroLote;
        this.cantidadActual   = builder.cantidadActual;
        this.fechaIngreso     = builder.fechaIngreso;
        this.fechaVencimiento = builder.fechaVencimiento;
        this.estado           = builder.estado;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaIngreso == null) this.fechaIngreso = LocalDate.now();
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Producto   producto;
        private Proveedor  proveedor;
        private String     numeroLote;
        private BigDecimal cantidadActual;
        private LocalDate  fechaIngreso;
        private LocalDate  fechaVencimiento;
        private EstadoLote estado = EstadoLote.activo;

        public Builder producto(Producto producto) {
            this.producto = producto;
            return this;
        }

        public Builder proveedor(Proveedor proveedor) {
            this.proveedor = proveedor;
            return this;
        }

        public Builder numeroLote(String numeroLote) {
            this.numeroLote = numeroLote;
            return this;
        }

        public Builder cantidadActual(BigDecimal cantidadActual) {
            this.cantidadActual = cantidadActual;
            return this;
        }

        public Builder fechaIngreso(LocalDate fechaIngreso) {
            this.fechaIngreso = fechaIngreso;
            return this;
        }

        public Builder fechaVencimiento(LocalDate fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
            return this;
        }

        public Builder estado(EstadoLote estado) {
            this.estado = estado;
            return this;
        }

        public Lote build() {
            if (producto == null) {
                throw new IllegalStateException("El producto es obligatorio");
            }
            if (proveedor == null) {
                throw new IllegalStateException("El proveedor es obligatorio");
            }
            if (numeroLote == null || numeroLote.isBlank()) {
                throw new IllegalStateException("El número de lote es obligatorio");
            }
            if (cantidadActual == null || cantidadActual.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException(
                        "La cantidad actual debe ser mayor o igual a 0");
            }
            if (fechaVencimiento == null) {
                throw new IllegalStateException("La fecha de vencimiento es obligatoria");
            }
            if (fechaIngreso != null && !fechaVencimiento.isAfter(fechaIngreso)) {
                throw new IllegalStateException(
                        "La fecha de vencimiento debe ser posterior a la fecha de ingreso");
            }
            return new Lote(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer    getIdLote()          { return idLote; }
    public Producto   getProducto()        { return producto; }
    public Proveedor  getProveedor()       { return proveedor; }
    public String     getNumeroLote()      { return numeroLote; }
    public BigDecimal getCantidadActual()  { return cantidadActual; }
    public LocalDate  getFechaIngreso()    { return fechaIngreso; }
    public LocalDate  getFechaVencimiento(){ return fechaVencimiento; }
    public EstadoLote getEstado()          { return estado; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdLote(Integer idLote)                    { this.idLote = idLote; }
    public void setProducto(Producto producto)               { this.producto = producto; }
    public void setProveedor(Proveedor proveedor)            { this.proveedor = proveedor; }
    public void setNumeroLote(String numeroLote)             { this.numeroLote = numeroLote; }
    public void setCantidadActual(BigDecimal cantidadActual) { this.cantidadActual = cantidadActual; }
    public void setFechaIngreso(LocalDate fechaIngreso)      { this.fechaIngreso = fechaIngreso; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public void setEstado(EstadoLote estado)                 { this.estado = estado; }
}