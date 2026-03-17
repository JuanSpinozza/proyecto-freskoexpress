package com.freskoexpress.api.domain.mantenimiento;

import com.freskoexpress.api.domain.logistica.Vehiculo;
import com.freskoexpress.api.shared.enums.TipoMantenimiento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "mantenimiento")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mantenimiento")
    private Integer idMantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMantenimiento tipo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "km_en_servicio", nullable = false)
    private Integer kmEnServicio;

    @Column(name = "fecha_servicio", nullable = false)
    private LocalDate fechaServicio;

    @Column(name = "prox_km")
    private Integer proxKm;

    @Column(name = "prox_fecha")
    private LocalDate proxFecha;

    // ── Constructores ─────────────────────────────────────────────────
    protected Mantenimiento() {}   // requerido por JPA

    private Mantenimiento(Builder builder) {
        this.vehiculo      = builder.vehiculo;
        this.tipo          = builder.tipo;
        this.descripcion   = builder.descripcion;
        this.kmEnServicio  = builder.kmEnServicio;
        this.fechaServicio = builder.fechaServicio;
        this.proxKm        = builder.proxKm;
        this.proxFecha     = builder.proxFecha;
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Vehiculo          vehiculo;
        private TipoMantenimiento tipo;
        private String            descripcion;
        private Integer           kmEnServicio;
        private LocalDate         fechaServicio;
        private Integer           proxKm;
        private LocalDate         proxFecha;

        public Builder vehiculo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
            return this;
        }

        public Builder tipo(TipoMantenimiento tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder kmEnServicio(Integer kmEnServicio) {
            this.kmEnServicio = kmEnServicio;
            return this;
        }

        public Builder fechaServicio(LocalDate fechaServicio) {
            this.fechaServicio = fechaServicio;
            return this;
        }

        public Builder proxKm(Integer proxKm) {
            this.proxKm = proxKm;
            return this;
        }

        public Builder proxFecha(LocalDate proxFecha) {
            this.proxFecha = proxFecha;
            return this;
        }

        public Mantenimiento build() {
            if (vehiculo == null) {
                throw new IllegalStateException("El vehículo es obligatorio");
            }
            if (tipo == null) {
                throw new IllegalStateException("El tipo de mantenimiento es obligatorio");
            }
            if (descripcion == null || descripcion.isBlank()) {
                throw new IllegalStateException("La descripción es obligatoria");
            }
            if (kmEnServicio == null || kmEnServicio < 0) {
                throw new IllegalStateException(
                        "El kilometraje en servicio debe ser mayor o igual a 0");
            }
            if (fechaServicio == null) {
                throw new IllegalStateException("La fecha de servicio es obligatoria");
            }
            if (proxKm != null && proxKm <= kmEnServicio) {
                throw new IllegalStateException(
                        "prox_km debe ser mayor al kilometraje en servicio");
            }
            if (proxFecha != null && !proxFecha.isAfter(fechaServicio)) {
                throw new IllegalStateException(
                        "prox_fecha debe ser posterior a la fecha de servicio");
            }
            return new Mantenimiento(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer          getIdMantenimiento() { return idMantenimiento; }
    public Vehiculo         getVehiculo()        { return vehiculo; }
    public TipoMantenimiento getTipo()           { return tipo; }
    public String           getDescripcion()     { return descripcion; }
    public Integer          getKmEnServicio()    { return kmEnServicio; }
    public LocalDate        getFechaServicio()   { return fechaServicio; }
    public Integer          getProxKm()          { return proxKm; }
    public LocalDate        getProxFecha()       { return proxFecha; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdMantenimiento(Integer idMantenimiento) { this.idMantenimiento = idMantenimiento; }
    public void setVehiculo(Vehiculo vehiculo)              { this.vehiculo = vehiculo; }
    public void setTipo(TipoMantenimiento tipo)             { this.tipo = tipo; }
    public void setDescripcion(String descripcion)          { this.descripcion = descripcion; }
    public void setKmEnServicio(Integer kmEnServicio)       { this.kmEnServicio = kmEnServicio; }
    public void setFechaServicio(LocalDate fechaServicio)   { this.fechaServicio = fechaServicio; }
    public void setProxKm(Integer proxKm)                   { this.proxKm = proxKm; }
    public void setProxFecha(LocalDate proxFecha)           { this.proxFecha = proxFecha; }
}