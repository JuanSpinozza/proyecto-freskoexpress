package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.shared.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "id_ruta")
    private Integer idRuta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado = EstadoPedido.confirmado;

    @Column(name = "fecha_pedido", nullable = false, updatable = false)
    private OffsetDateTime fechaPedido;

    @Column(name = "fecha_entrega_req", nullable = false)
    private LocalDate fechaEntregaReq;

    @Column(name = "ventana_inicio")
    private LocalTime ventanaInicio;

    @Column(name = "ventana_fin")
    private LocalTime ventanaFin;

    @Column(name = "orden_en_ruta")
    private Integer ordenEnRuta;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.fechaPedido == null) this.fechaPedido = OffsetDateTime.now();
    }
}
