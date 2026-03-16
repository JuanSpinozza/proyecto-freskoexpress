package com.freskoexpress.api.domain.inventario;

import com.freskoexpress.domain.proveedor.Proveedor;
import com.freskoexpress.shared.enums.EstadoLote;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lote")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "numero_lote", nullable = false, length = 50)
    private String numeroLote;

    @Column(name = "cantidad_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadActual;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLote estado = EstadoLote.activo;

    @PrePersist
    protected void onCreate() {
        if (this.fechaIngreso == null) this.fechaIngreso = LocalDate.now();
    }
}
