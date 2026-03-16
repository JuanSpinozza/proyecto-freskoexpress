package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.domain.inventario.Lote;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetallePedido {

    @EmbeddedId
    private DetallePedidoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idLote")
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // GENERATED ALWAYS en BD — no enviamos en INSERT
    @Column(precision = 12, scale = 2, insertable = false, updatable = false)
    private BigDecimal subtotal;

    @Column(name = "lat_entrega", precision = 10, scale = 7)
    private BigDecimal latEntrega;

    @Column(name = "lng_entrega", precision = 10, scale = 7)
    private BigDecimal lngEntrega;
}
