package com.freskoexpress.api.domain.proveedor;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "certificado_sanitario")
public class CertificadoSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificado")
    private Integer idCertificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(name = "url_archivo", nullable = false, length = 500)
    private String urlArchivo;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_carga", nullable = false, updatable = false)
    private OffsetDateTime fechaCarga;

    // ── Constructores ─────────────────────────────────────────────────
    protected CertificadoSanitario() {}   // requerido por JPA

    private CertificadoSanitario(Builder builder) {
        this.proveedor        = builder.proveedor;
        this.tipo             = builder.tipo;
        this.urlArchivo       = builder.urlArchivo;
        this.fechaVencimiento = builder.fechaVencimiento;
    }

    @PrePersist
    protected void onCreate() { this.fechaCarga = OffsetDateTime.now(); }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Proveedor  proveedor;
        private String     tipo;
        private String     urlArchivo;
        private LocalDate  fechaVencimiento;

        public Builder proveedor(Proveedor proveedor) {
            this.proveedor = proveedor;
            return this;
        }

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder urlArchivo(String urlArchivo) {
            this.urlArchivo = urlArchivo;
            return this;
        }

        public Builder fechaVencimiento(LocalDate fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
            return this;
        }

        public CertificadoSanitario build() {
            if (proveedor == null) {
                throw new IllegalStateException("El proveedor es obligatorio");
            }
            if (tipo == null || tipo.isBlank()) {
                throw new IllegalStateException("El tipo de certificado es obligatorio");
            }
            if (urlArchivo == null || urlArchivo.isBlank()) {
                throw new IllegalStateException("La URL del archivo es obligatoria");
            }
            if (fechaVencimiento == null) {
                throw new IllegalStateException("La fecha de vencimiento es obligatoria");
            }
            if (!fechaVencimiento.isAfter(LocalDate.now())) {
                throw new IllegalStateException(
                        "La fecha de vencimiento debe ser posterior a la fecha actual");
            }
            return new CertificadoSanitario(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer       getIdCertificado()  { return idCertificado; }
    public Proveedor     getProveedor()      { return proveedor; }
    public String        getTipo()           { return tipo; }
    public String        getUrlArchivo()     { return urlArchivo; }
    public LocalDate     getFechaVencimiento(){ return fechaVencimiento; }
    public OffsetDateTime getFechaCarga()    { return fechaCarga; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdCertificado(Integer idCertificado)    { this.idCertificado = idCertificado; }
    public void setProveedor(Proveedor proveedor)          { this.proveedor = proveedor; }
    public void setTipo(String tipo)                       { this.tipo = tipo; }
    public void setUrlArchivo(String urlArchivo)           { this.urlArchivo = urlArchivo; }
    public void setFechaVencimiento(LocalDate fecha)       { this.fechaVencimiento = fecha; }
    public void setFechaCarga(OffsetDateTime fechaCarga)   { this.fechaCarga = fechaCarga; }
}