package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.api.domain.auth.Usuario;
import com.freskoexpress.api.shared.enums.EstadoRuta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ruta")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Integer idRuta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_conductor", nullable = false)
    private Usuario conductor;

    @Column(name = "fecha_ruta", nullable = false)
    private LocalDate fechaRuta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRuta estado = EstadoRuta.planificada;

    @Column(name = "hora_inicio_real")
    private OffsetDateTime horaInicioReal;

    @Column(name = "hora_fin_real")
    private OffsetDateTime horaFinReal;

    @Column(name = "km_recorridos", precision = 8, scale = 2)
    private BigDecimal kmRecorridos;

    // ── Constructores ─────────────────────────────────────────────────
    protected Ruta() {}

    private Ruta(Builder builder) {
        this.vehiculo      = builder.vehiculo;
        this.conductor     = builder.conductor;
        this.fechaRuta     = builder.fechaRuta;
        this.estado        = builder.estado;
        this.horaInicioReal = builder.horaInicioReal;
        this.horaFinReal   = builder.horaFinReal;
        this.kmRecorridos  = builder.kmRecorridos;
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Vehiculo      vehiculo;
        private Usuario       conductor;
        private LocalDate     fechaRuta;
        private EstadoRuta    estado        = EstadoRuta.planificada;
        private OffsetDateTime horaInicioReal;
        private OffsetDateTime horaFinReal;
        private BigDecimal    kmRecorridos;

        public Builder vehiculo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
            return this;
        }

        public Builder conductor(Usuario conductor) {
            this.conductor = conductor;
            return this;
        }

        public Builder fechaRuta(LocalDate fechaRuta) {
            this.fechaRuta = fechaRuta;
            return this;
        }

        public Builder estado(EstadoRuta estado) {
            this.estado = estado;
            return this;
        }

        public Builder horaInicioReal(OffsetDateTime horaInicioReal) {
            this.horaInicioReal = horaInicioReal;
            return this;
        }

        public Builder horaFinReal(OffsetDateTime horaFinReal) {
            this.horaFinReal = horaFinReal;
            return this;
        }

        public Builder kmRecorridos(BigDecimal kmRecorridos) {
            this.kmRecorridos = kmRecorridos;
            return this;
        }

        public Ruta build() {
            if (vehiculo == null) {
                throw new IllegalStateException("El vehículo es obligatorio");
            }
            if (conductor == null) {
                throw new IllegalStateException("El conductor es obligatorio");
            }
            if (fechaRuta == null) {
                throw new IllegalStateException("La fecha de ruta es obligatoria");
            }
            if (horaFinReal != null && horaInicioReal != null
                    && !horaFinReal.isAfter(horaInicioReal)) {
                throw new IllegalStateException(
                        "hora_fin_real debe ser posterior a hora_inicio_real");
            }
            if (kmRecorridos != null && kmRecorridos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException(
                        "Los km recorridos no pueden ser negativos");
            }
            return new Ruta(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer        getIdRuta()         { return idRuta; }
    public Vehiculo       getVehiculo()       { return vehiculo; }
    public Usuario        getConductor()      { return conductor; }
    public LocalDate      getFechaRuta()      { return fechaRuta; }
    public EstadoRuta     getEstado()         { return estado; }
    public OffsetDateTime getHoraInicioReal() { return horaInicioReal; }
    public OffsetDateTime getHoraFinReal()    { return horaFinReal; }
    public BigDecimal     getKmRecorridos()   { return kmRecorridos; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdRuta(Integer idRuta)                   { this.idRuta = idRuta; }
    public void setVehiculo(Vehiculo vehiculo)               { this.vehiculo = vehiculo; }
    public void setConductor(Usuario conductor)              { this.conductor = conductor; }
    public void setFechaRuta(LocalDate fechaRuta)           { this.fechaRuta = fechaRuta; }
    public void setEstado(EstadoRuta estado)                { this.estado = estado; }
    public void setHoraInicioReal(OffsetDateTime horaInicioReal) { this.horaInicioReal = horaInicioReal; }
    public void setHoraFinReal(OffsetDateTime horaFinReal)  { this.horaFinReal = horaFinReal; }
    public void setKmRecorridos(BigDecimal kmRecorridos)    { this.kmRecorridos = kmRecorridos; }
}