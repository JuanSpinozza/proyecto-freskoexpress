package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.api.shared.enums.TipoVehiculo;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    @Column(nullable = false, unique = true, length = 15)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(name = "capacidad_kg", nullable = false, precision = 8, scale = 2)
    private BigDecimal capacidadKg;

    @Column(nullable = false)
    private Integer kilometraje = 0;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(nullable = false, length = 100)
    private String ciudad;

    // ── Constructores ─────────────────────────────────────────────────
    protected Vehiculo() {}   // requerido por JPA

    private Vehiculo(Builder builder) {
        this.placa       = builder.placa;
        this.tipo        = builder.tipo;
        this.marca       = builder.marca;
        this.modelo      = builder.modelo;
        this.capacidadKg = builder.capacidadKg;
        this.kilometraje = builder.kilometraje;
        this.disponible  = builder.disponible;
        this.ciudad      = builder.ciudad;
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String      placa;
        private TipoVehiculo tipo;
        private String      marca;
        private String      modelo;
        private BigDecimal  capacidadKg;
        private Integer     kilometraje = 0;
        private Boolean     disponible  = true;
        private String      ciudad;

        public Builder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public Builder tipo(TipoVehiculo tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder marca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder modelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public Builder capacidadKg(BigDecimal capacidadKg) {
            this.capacidadKg = capacidadKg;
            return this;
        }

        public Builder kilometraje(Integer kilometraje) {
            this.kilometraje = kilometraje;
            return this;
        }

        public Builder disponible(Boolean disponible) {
            this.disponible = disponible;
            return this;
        }

        public Builder ciudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }

        public Vehiculo build() {
            if (placa == null || placa.isBlank()) {
                throw new IllegalStateException("La placa es obligatoria");
            }
            if (tipo == null) {
                throw new IllegalStateException("El tipo de vehículo es obligatorio");
            }
            if (marca == null || marca.isBlank()) {
                throw new IllegalStateException("La marca es obligatoria");
            }
            if (modelo == null || modelo.isBlank()) {
                throw new IllegalStateException("El modelo es obligatorio");
            }
            if (capacidadKg == null || capacidadKg.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalStateException("La capacidad en kg debe ser mayor a 0");
            }
            if (kilometraje != null && kilometraje < 0) {
                throw new IllegalStateException("El kilometraje no puede ser negativo");
            }
            if (ciudad == null || ciudad.isBlank()) {
                throw new IllegalStateException("La ciudad es obligatoria");
            }
            return new Vehiculo(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer      getIdVehiculo() { return idVehiculo; }
    public String       getPlaca()      { return placa; }
    public TipoVehiculo getTipo()       { return tipo; }
    public String       getMarca()      { return marca; }
    public String       getModelo()     { return modelo; }
    public BigDecimal   getCapacidadKg(){ return capacidadKg; }
    public Integer      getKilometraje(){ return kilometraje; }
    public Boolean      getDisponible() { return disponible; }
    public String       getCiudad()     { return ciudad; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdVehiculo(Integer idVehiculo)      { this.idVehiculo = idVehiculo; }
    public void setPlaca(String placa)                 { this.placa = placa; }
    public void setTipo(TipoVehiculo tipo)             { this.tipo = tipo; }
    public void setMarca(String marca)                 { this.marca = marca; }
    public void setModelo(String modelo)               { this.modelo = modelo; }
    public void setCapacidadKg(BigDecimal capacidadKg) { this.capacidadKg = capacidadKg; }
    public void setKilometraje(Integer kilometraje)    { this.kilometraje = kilometraje; }
    public void setDisponible(Boolean disponible)      { this.disponible = disponible; }
    public void setCiudad(String ciudad)               { this.ciudad = ciudad; }
}