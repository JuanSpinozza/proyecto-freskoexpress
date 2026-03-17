package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.api.shared.enums.EstadoPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "id_ruta")
    private Integer idRuta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado = EstadoPedido.confirmado;

    @Column(name = "fecha_pedido", nullable = false, updatable = false)
    private OffsetDateTime fechaPedido;

    @Column(name = "fecha_entrega_req", nullable = false)
    private LocalDate fechaEntregaReq;

    @Column(name = "ventana_inicio")
    private LocalTime ventanaInicio;

    @Column(name = "ventana_fin")
    private LocalTime ventanaFin;

    @Column(name = "orden_en_ruta")
    private Integer ordenEnRuta;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

    // ── Constructores ─────────────────────────────────────────────────
    protected Pedido() {}   // requerido por JPA

    private Pedido(Builder builder) {
        this.cliente        = builder.cliente;
        this.idRuta         = builder.idRuta;
        this.estado         = builder.estado;
        this.fechaEntregaReq = builder.fechaEntregaReq;
        this.ventanaInicio  = builder.ventanaInicio;
        this.ventanaFin     = builder.ventanaFin;
        this.ordenEnRuta    = builder.ordenEnRuta;
        this.total          = builder.total;
        this.detalles       = builder.detalles;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaPedido == null) this.fechaPedido = OffsetDateTime.now();
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Cliente      cliente;
        private Integer      idRuta;
        private EstadoPedido estado         = EstadoPedido.confirmado;
        private LocalDate    fechaEntregaReq;
        private LocalTime    ventanaInicio;
        private LocalTime    ventanaFin;
        private Integer      ordenEnRuta;
        private BigDecimal   total          = BigDecimal.ZERO;
        // @Builder.Default — inicializado aquí igual que en el campo de la entidad
        private List<DetallePedido> detalles = new ArrayList<>();

        public Builder cliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder idRuta(Integer idRuta) {
            this.idRuta = idRuta;
            return this;
        }

        public Builder estado(EstadoPedido estado) {
            this.estado = estado;
            return this;
        }

        public Builder fechaEntregaReq(LocalDate fechaEntregaReq) {
            this.fechaEntregaReq = fechaEntregaReq;
            return this;
        }

        public Builder ventanaInicio(LocalTime ventanaInicio) {
            this.ventanaInicio = ventanaInicio;
            return this;
        }

        public Builder ventanaFin(LocalTime ventanaFin) {
            this.ventanaFin = ventanaFin;
            return this;
        }

        public Builder ordenEnRuta(Integer ordenEnRuta) {
            this.ordenEnRuta = ordenEnRuta;
            return this;
        }

        public Builder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Builder detalles(List<DetallePedido> detalles) {
            this.detalles = detalles;
            return this;
        }

        public Pedido build() {
            if (cliente == null) {
                throw new IllegalStateException("El cliente es obligatorio");
            }
            if (fechaEntregaReq == null) {
                throw new IllegalStateException("La fecha de entrega requerida es obligatoria");
            }
            if (total != null && total.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("El total no puede ser negativo");
            }
            if (ventanaInicio != null && ventanaFin != null
                    && !ventanaFin.isAfter(ventanaInicio)) {
                throw new IllegalStateException(
                        "ventana_fin debe ser posterior a ventana_inicio");
            }
            if (ordenEnRuta != null && ordenEnRuta <= 0) {
                throw new IllegalStateException("orden_en_ruta debe ser mayor a 0");
            }
            return new Pedido(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer          getIdPedido()       { return idPedido; }
    public Cliente          getCliente()        { return cliente; }
    public Integer          getIdRuta()         { return idRuta; }
    public EstadoPedido     getEstado()         { return estado; }
    public OffsetDateTime   getFechaPedido()    { return fechaPedido; }
    public LocalDate        getFechaEntregaReq(){ return fechaEntregaReq; }
    public LocalTime        getVentanaInicio()  { return ventanaInicio; }
    public LocalTime        getVentanaFin()     { return ventanaFin; }
    public Integer          getOrdenEnRuta()    { return ordenEnRuta; }
    public BigDecimal       getTotal()          { return total; }
    public List<DetallePedido> getDetalles()    { return detalles; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdPedido(Integer idPedido)           { this.idPedido = idPedido; }
    public void setCliente(Cliente cliente)             { this.cliente = cliente; }
    public void setIdRuta(Integer idRuta)               { this.idRuta = idRuta; }
    public void setEstado(EstadoPedido estado)          { this.estado = estado; }
    public void setFechaPedido(OffsetDateTime fecha)    { this.fechaPedido = fecha; }
    public void setFechaEntregaReq(LocalDate fecha)     { this.fechaEntregaReq = fecha; }
    public void setVentanaInicio(LocalTime ventana)     { this.ventanaInicio = ventana; }
    public void setVentanaFin(LocalTime ventana)        { this.ventanaFin = ventana; }
    public void setOrdenEnRuta(Integer orden)           { this.ordenEnRuta = orden; }
    public void setTotal(BigDecimal total)              { this.total = total; }
    public void setDetalles(List<DetallePedido> detalles){ this.detalles = detalles; }
}