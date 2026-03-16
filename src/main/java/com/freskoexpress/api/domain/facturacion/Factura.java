package com.freskoexpress.api.domain.facturacion;

import com.freskoexpress.domain.pedido.Pedido;
import com.freskoexpress.shared.enums.EstadoFactura;
import com.freskoexpress.shared.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "factura")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;

    @Column(name = "numero_factura", nullable = false, unique = true, length = 30)
    private String numeroFactura;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoFactura estado = EstadoFactura.pendiente;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "referencia_pago", length = 100)
    private String referenciaPago;

    @Column(name = "fecha_emision", nullable = false, updatable = false)
    private OffsetDateTime fechaEmision;

    @Column(name = "fecha_pago")
    private OffsetDateTime fechaPago;

    @PrePersist
    protected void onCreate() {
        if (this.fechaEmision == null) this.fechaEmision = OffsetDateTime.now();
    }
}
