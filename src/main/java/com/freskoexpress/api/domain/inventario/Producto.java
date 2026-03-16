package com.freskoexpress.api.domain.inventario;

import com.freskoexpress.domain.proveedor.Proveedor;
import com.freskoexpress.shared.enums.CategoriaProducto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProducto categoria;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

    @Column(name = "temp_min_c", precision = 5, scale = 2)
    private BigDecimal tempMinC;

    @Column(name = "temp_max_c", precision = 5, scale = 2)
    private BigDecimal tempMaxC;

    @Column(name = "stock_minimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal stockMinimo = BigDecimal.ZERO;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private Boolean activo = true;
}
