package com.freskoexpress.api.domain.iot;

import com.freskoexpress.api.domain.logistica.Vehiculo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "lectura_sensor")
public class LecturaSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lectura")
    private Long idLectura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "id_ruta")
    private Integer idRuta;

    @Column(name = "temperatura_c", nullable = false, precision = 5, scale = 2)
    private BigDecimal temperaturaC;

    @Column(name = "humedad_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal humedadPct;

    @Column(name = "fuera_de_rango", nullable = false)
    private Boolean fueraDeRango = false;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime timestamp;

    // ── Constructores ─────────────────────────────────────────────────
    protected LecturaSensor() {}   // requerido por JPA

    private LecturaSensor(Builder builder) {
        this.vehiculo     = builder.vehiculo;
        this.idRuta       = builder.idRuta;
        this.temperaturaC = builder.temperaturaC;
        this.humedadPct   = builder.humedadPct;
        this.fueraDeRango = builder.fueraDeRango;
        // timestamp no va en el Builder — lo asigna @PrePersist
    }

    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) this.timestamp = OffsetDateTime.now();
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Vehiculo   vehiculo;
        private Integer    idRuta;
        private BigDecimal temperaturaC;
        private BigDecimal humedadPct;
        private Boolean    fueraDeRango = false;

        public Builder vehiculo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
            return this;
        }

        public Builder idRuta(Integer idRuta) {
            this.idRuta = idRuta;
            return this;
        }

        public Builder temperaturaC(BigDecimal temperaturaC) {
            this.temperaturaC = temperaturaC;
            return this;
        }

        public Builder humedadPct(BigDecimal humedadPct) {
            this.humedadPct = humedadPct;
            return this;
        }

        public Builder fueraDeRango(Boolean fueraDeRango) {
            this.fueraDeRango = fueraDeRango;
            return this;
        }

        public LecturaSensor build() {
            if (vehiculo == null) {
                throw new IllegalStateException("El vehículo es obligatorio");
            }
            if (temperaturaC == null) {
                throw new IllegalStateException("La temperatura es obligatoria");
            }
            if (humedadPct == null) {
                throw new IllegalStateException("La humedad es obligatoria");
            }
            if (humedadPct.compareTo(BigDecimal.ZERO) < 0
                    || humedadPct.compareTo(new BigDecimal("100")) > 0) {
                throw new IllegalStateException(
                        "La humedad debe estar entre 0 y 100");
            }
            return new LecturaSensor(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Long          getIdLectura()   { return idLectura; }
    public Vehiculo      getVehiculo()    { return vehiculo; }
    public Integer       getIdRuta()      { return idRuta; }
    public BigDecimal    getTemperaturaC(){ return temperaturaC; }
    public BigDecimal    getHumedadPct()  { return humedadPct; }
    public Boolean       getFueraDeRango(){ return fueraDeRango; }
    public OffsetDateTime getTimestamp()  { return timestamp; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdLectura(Long idLectura)           { this.idLectura = idLectura; }
    public void setVehiculo(Vehiculo vehiculo)         { this.vehiculo = vehiculo; }
    public void setIdRuta(Integer idRuta)              { this.idRuta = idRuta; }
    public void setTemperaturaC(BigDecimal temp)       { this.temperaturaC = temp; }
    public void setHumedadPct(BigDecimal humedad)      { this.humedadPct = humedad; }
    public void setFueraDeRango(Boolean fueraDeRango)  { this.fueraDeRango = fueraDeRango; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
}
