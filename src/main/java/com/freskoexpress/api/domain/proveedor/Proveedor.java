package com.freskoexpress.api.domain.proveedor;

import com.freskoexpress.api.domain.auth.Usuario;
import com.freskoexpress.api.shared.enums.EstadoProveedor;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(nullable = false, unique = true, length = 20)
    private String nit;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "contacto_correo", nullable = false, length = 150)
    private String contactoCorreo;

    @Column(name = "contacto_telefono", nullable = false, length = 20)
    private String contactoTelefono;

    @Column(name = "capacidad_semanal", nullable = false, precision = 10, scale = 2)
    private BigDecimal capacidadSemanal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProveedor estado = EstadoProveedor.pendiente;

    @Column(name = "razon_rechazo", columnDefinition = "TEXT")
    private String razonRechazo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_revisor")
    private Usuario usuarioRevisor;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private OffsetDateTime fechaRegistro;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CertificadoSanitario> certificados = new ArrayList<>();

    // ── Constructores ─────────────────────────────────────────────────
    protected Proveedor() {}   // requerido por JPA

    private Proveedor(Builder builder) {
        this.nit               = builder.nit;
        this.razonSocial       = builder.razonSocial;
        this.contactoCorreo    = builder.contactoCorreo;
        this.contactoTelefono  = builder.contactoTelefono;
        this.capacidadSemanal  = builder.capacidadSemanal;
        this.estado            = builder.estado;
        this.razonRechazo      = builder.razonRechazo;
        this.usuarioRevisor    = builder.usuarioRevisor;
        this.certificados      = builder.certificados;
    }

    @PrePersist
    protected void onCreate() { this.fechaRegistro = OffsetDateTime.now(); }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String          nit;
        private String          razonSocial;
        private String          contactoCorreo;
        private String          contactoTelefono;
        private BigDecimal      capacidadSemanal;
        private EstadoProveedor estado           = EstadoProveedor.pendiente;
        private String          razonRechazo;
        private Usuario         usuarioRevisor;
        // @Builder.Default en Lombok inicializa el campo con el valor indicado.
        // Sin Lombok se logra lo mismo asignando el valor directamente aquí.
        private List<CertificadoSanitario> certificados = new ArrayList<>();

        public Builder nit(String nit) {
            this.nit = nit;
            return this;
        }

        public Builder razonSocial(String razonSocial) {
            this.razonSocial = razonSocial;
            return this;
        }

        public Builder contactoCorreo(String contactoCorreo) {
            this.contactoCorreo = contactoCorreo;
            return this;
        }

        public Builder contactoTelefono(String contactoTelefono) {
            this.contactoTelefono = contactoTelefono;
            return this;
        }

        public Builder capacidadSemanal(BigDecimal capacidadSemanal) {
            this.capacidadSemanal = capacidadSemanal;
            return this;
        }

        public Builder estado(EstadoProveedor estado) {
            this.estado = estado;
            return this;
        }

        public Builder razonRechazo(String razonRechazo) {
            this.razonRechazo = razonRechazo;
            return this;
        }

        public Builder usuarioRevisor(Usuario usuarioRevisor) {
            this.usuarioRevisor = usuarioRevisor;
            return this;
        }

        public Builder certificados(List<CertificadoSanitario> certificados) {
            this.certificados = certificados;
            return this;
        }

        public Proveedor build() {
            if (nit == null || nit.isBlank()) {
                throw new IllegalStateException("El NIT es obligatorio");
            }
            if (razonSocial == null || razonSocial.isBlank()) {
                throw new IllegalStateException("La razón social es obligatoria");
            }
            if (contactoCorreo == null || contactoCorreo.isBlank()) {
                throw new IllegalStateException("El correo de contacto es obligatorio");
            }
            if (contactoTelefono == null || contactoTelefono.isBlank()) {
                throw new IllegalStateException("El teléfono de contacto es obligatorio");
            }
            if (capacidadSemanal == null || capacidadSemanal.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalStateException(
                        "La capacidad semanal debe ser mayor a 0");
            }
            if (estado == EstadoProveedor.rechazado
                    && (razonRechazo == null || razonRechazo.isBlank())) {
                throw new IllegalStateException(
                        "La razón de rechazo es obligatoria cuando el estado es rechazado");
            }
            return new Proveedor(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer          getIdProveedor()     { return idProveedor; }
    public String           getNit()             { return nit; }
    public String           getRazonSocial()     { return razonSocial; }
    public String           getContactoCorreo()  { return contactoCorreo; }
    public String           getContactoTelefono(){ return contactoTelefono; }
    public BigDecimal       getCapacidadSemanal(){ return capacidadSemanal; }
    public EstadoProveedor  getEstado()          { return estado; }
    public String           getRazonRechazo()    { return razonRechazo; }
    public Usuario          getUsuarioRevisor()  { return usuarioRevisor; }
    public OffsetDateTime   getFechaRegistro()   { return fechaRegistro; }
    public List<CertificadoSanitario> getCertificados() { return certificados; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdProveedor(Integer idProveedor)         { this.idProveedor = idProveedor; }
    public void setNit(String nit)                          { this.nit = nit; }
    public void setRazonSocial(String razonSocial)          { this.razonSocial = razonSocial; }
    public void setContactoCorreo(String contactoCorreo)    { this.contactoCorreo = contactoCorreo; }
    public void setContactoTelefono(String tel)             { this.contactoTelefono = tel; }
    public void setCapacidadSemanal(BigDecimal capacidad)   { this.capacidadSemanal = capacidad; }
    public void setEstado(EstadoProveedor estado)           { this.estado = estado; }
    public void setRazonRechazo(String razonRechazo)        { this.razonRechazo = razonRechazo; }
    public void setUsuarioRevisor(Usuario usuarioRevisor)   { this.usuarioRevisor = usuarioRevisor; }
    public void setFechaRegistro(OffsetDateTime fecha)      { this.fechaRegistro = fecha; }
    public void setCertificados(List<CertificadoSanitario> certificados) { this.certificados = certificados; }
}