package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.api.domain.auth.Usuario;
import com.freskoexpress.api.shared.enums.TipoCliente;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(nullable = false, unique = true, length = 20)
    private String nit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCliente tipo;

    @Column(nullable = false, length = 300)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(name = "coordenada_lat", precision = 10, scale = 7)
    private BigDecimal coordenadaLat;

    @Column(name = "coordenada_lng", precision = 10, scale = 7)
    private BigDecimal coordenadaLng;

    // ── Constructores ─────────────────────────────────────────────────
    protected Cliente() {}   // requerido por JPA

    private Cliente(Builder builder) {
        this.usuario       = builder.usuario;
        this.razonSocial   = builder.razonSocial;
        this.nit           = builder.nit;
        this.tipo          = builder.tipo;
        this.direccion     = builder.direccion;
        this.ciudad        = builder.ciudad;
        this.coordenadaLat = builder.coordenadaLat;
        this.coordenadaLng = builder.coordenadaLng;
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Usuario     usuario;
        private String      razonSocial;
        private String      nit;
        private TipoCliente tipo;
        private String      direccion;
        private String      ciudad;
        private BigDecimal  coordenadaLat;
        private BigDecimal  coordenadaLng;

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder razonSocial(String razonSocial) {
            this.razonSocial = razonSocial;
            return this;
        }

        public Builder nit(String nit) {
            this.nit = nit;
            return this;
        }

        public Builder tipo(TipoCliente tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder ciudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }

        public Builder coordenadaLat(BigDecimal coordenadaLat) {
            this.coordenadaLat = coordenadaLat;
            return this;
        }

        public Builder coordenadaLng(BigDecimal coordenadaLng) {
            this.coordenadaLng = coordenadaLng;
            return this;
        }

        public Cliente build() {
            if (usuario == null) {
                throw new IllegalStateException("El usuario es obligatorio");
            }
            if (razonSocial == null || razonSocial.isBlank()) {
                throw new IllegalStateException("La razón social es obligatoria");
            }
            if (nit == null || nit.isBlank()) {
                throw new IllegalStateException("El NIT es obligatorio");
            }
            if (tipo == null) {
                throw new IllegalStateException("El tipo de cliente es obligatorio");
            }
            if (direccion == null || direccion.isBlank()) {
                throw new IllegalStateException("La dirección es obligatoria");
            }
            if (ciudad == null || ciudad.isBlank()) {
                throw new IllegalStateException("La ciudad es obligatoria");
            }
            if (coordenadaLat != null && coordenadaLng == null
                    || coordenadaLat == null && coordenadaLng != null) {
                throw new IllegalStateException(
                        "coordenada_lat y coordenada_lng deben proporcionarse juntas o ninguna");
            }
            return new Cliente(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer     getIdCliente()     { return idCliente; }
    public Usuario     getUsuario()       { return usuario; }
    public String      getRazonSocial()   { return razonSocial; }
    public String      getNit()           { return nit; }
    public TipoCliente getTipo()          { return tipo; }
    public String      getDireccion()     { return direccion; }
    public String      getCiudad()        { return ciudad; }
    public BigDecimal  getCoordenadaLat() { return coordenadaLat; }
    public BigDecimal  getCoordenadaLng() { return coordenadaLng; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdCliente(Integer idCliente)         { this.idCliente = idCliente; }
    public void setUsuario(Usuario usuario)             { this.usuario = usuario; }
    public void setRazonSocial(String razonSocial)      { this.razonSocial = razonSocial; }
    public void setNit(String nit)                      { this.nit = nit; }
    public void setTipo(TipoCliente tipo)               { this.tipo = tipo; }
    public void setDireccion(String direccion)          { this.direccion = direccion; }
    public void setCiudad(String ciudad)                { this.ciudad = ciudad; }
    public void setCoordenadaLat(BigDecimal lat)        { this.coordenadaLat = lat; }
    public void setCoordenadaLng(BigDecimal lng)        { this.coordenadaLng = lng; }
}