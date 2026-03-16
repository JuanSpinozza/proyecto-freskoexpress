package com.freskoexpress.api.domain.iot;

import com.freskoexpress.domain.inventario.Producto;
import com.freskoexpress.domain.logistica.Vehiculo;
import com.freskoexpress.shared.enums.SeveridadAlerta;
import com.freskoexpress.shared.enums.TipoAlerta;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "alerta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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

    @PrePersist
    protected void onCreate() {
        if (this.fechaAlerta == null) this.fechaAlerta = OffsetDateTime.now();
    }
}
