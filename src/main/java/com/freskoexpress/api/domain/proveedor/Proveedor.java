package com.freskoexpress.api.domain.proveedor;

import com.freskoexpress.domain.auth.Usuario;
import com.freskoexpress.shared.enums.EstadoProveedor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proveedor")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
    @Builder.Default
    private List<CertificadoSanitario> certificados = new ArrayList<>();

    @PrePersist
    protected void onCreate() { this.fechaRegistro = OffsetDateTime.now(); }
}
