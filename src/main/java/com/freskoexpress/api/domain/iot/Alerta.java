package com.freskoexpress.api.domain.iot;

import com.freskoexpress.api.domain.inventario.Producto;
import com.freskoexpress.api.domain.logistica.Vehiculo;
import com.freskoexpress.api.shared.enums.SeveridadAlerta;
import com.freskoexpress.api.shared.enums.TipoAlerta;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlerta tipo;

    @Column(name = "id_lectura")
    private Long idLectura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeveridadAlerta severidad = SeveridadAlerta.advertencia;

    @Column(nullable = false, length = 300)
    private String mensaje;

    @Column(nullable = false)
    private Boolean reconocida = false;

    @Column(name = "fecha_alerta", nullable = false, updatable = false)
    private OffsetDateTime fechaAlerta;

    // ── Constructores ─────────────────────────────────────────────────
    protected Alerta() {}   // requerido por JPA

    private Alerta(Builder builder) {
        this.tipo       = builder.tipo;
        this.idLectura  = builder.idLectura;
        this.vehiculo   = builder.vehiculo;
        this.producto   = builder.producto;
        this.severidad  = builder.severidad;
        this.mensaje    = builder.mensaje;
        this.reconocida = builder.reconocida;
        // fechaAlerta no va en el Builder — la asigna @PrePersist
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaAlerta == null) this.fechaAlerta = OffsetDateTime.now();
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private TipoAlerta      tipo;
        private Long            idLectura;
        private Vehiculo        vehiculo;
        private Producto        producto;
        private SeveridadAlerta severidad  = SeveridadAlerta.advertencia;
        private String          mensaje;
        private Boolean         reconocida = false;

        public Builder tipo(TipoAlerta tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder idLectura(Long idLectura) {
            this.idLectura = idLectura;
            return this;
        }

        public Builder vehiculo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
            return this;
        }

        public Builder producto(Producto producto) {
            this.producto = producto;
            return this;
        }

        public Builder severidad(SeveridadAlerta severidad) {
            this.severidad = severidad;
            return this;
        }

        public Builder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public Builder reconocida(Boolean reconocida) {
            this.reconocida = reconocida;
            return this;
        }

        public Alerta build() {
            if (tipo == null) {
                throw new IllegalStateException("El tipo de alerta es obligatorio");
            }
            if (mensaje == null || mensaje.isBlank()) {
                throw new IllegalStateException("El mensaje de la alerta es obligatorio");
            }
            if (severidad == null) {
                throw new IllegalStateException("La severidad es obligatoria");
            }
            // Regla de negocio: al menos uno de los tres referencias debe estar presente
            if (idLectura == null && vehiculo == null && producto == null) {
                throw new IllegalStateException(
                        "La alerta debe estar asociada a una lectura, un vehículo o un producto");
            }
            // Regla de negocio: alerta de temperatura requiere idLectura y vehiculo
            if (tipo == TipoAlerta.temperatura
                    && (idLectura == null || vehiculo == null)) {
                throw new IllegalStateException(
                        "Las alertas de temperatura requieren idLectura y vehiculo");
            }
            // Regla de negocio: alertas de reposicion y vencimiento requieren producto
            if ((tipo == TipoAlerta.reposicion || tipo == TipoAlerta.vencimiento)
                    && producto == null) {
                throw new IllegalStateException(
                        "Las alertas de reposición y vencimiento requieren producto");
            }
            // Regla de negocio: alertas de mantenimiento requieren vehiculo
            if (tipo == TipoAlerta.mantenimiento && vehiculo == null) {
                throw new IllegalStateException(
                        "Las alertas de mantenimiento requieren vehiculo");
            }
            return new Alerta(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer         getIdAlerta()   { return idAlerta; }
    public TipoAlerta      getTipo()       { return tipo; }
    public Long            getIdLectura()  { return idLectura; }
    public Vehiculo        getVehiculo()   { return vehiculo; }
    public Producto        getProducto()   { return producto; }
    public SeveridadAlerta getSeveridad()  { return severidad; }
    public String          getMensaje()    { return mensaje; }
    public Boolean         getReconocida() { return reconocida; }
    public OffsetDateTime  getFechaAlerta(){ return fechaAlerta; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdAlerta(Integer idAlerta)          { this.idAlerta = idAlerta; }
    public void setTipo(TipoAlerta tipo)               { this.tipo = tipo; }
    public void setIdLectura(Long idLectura)           { this.idLectura = idLectura; }
    public void setVehiculo(Vehiculo vehiculo)         { this.vehiculo = vehiculo; }
    public void setProducto(Producto producto)         { this.producto = producto; }
    public void setSeveridad(SeveridadAlerta severidad){ this.severidad = severidad; }
    public void setMensaje(String mensaje)             { this.mensaje = mensaje; }
    public void setReconocida(Boolean reconocida)      { this.reconocida = reconocida; }
    public void setFechaAlerta(OffsetDateTime fecha)   { this.fechaAlerta = fecha; }
}