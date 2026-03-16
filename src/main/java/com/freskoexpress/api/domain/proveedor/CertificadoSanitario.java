package com.freskoexpress.api.domain.proveedor;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "certificado_sanitario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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

    @PrePersist
    protected void onCreate() { this.fechaCarga = OffsetDateTime.now(); }
}
