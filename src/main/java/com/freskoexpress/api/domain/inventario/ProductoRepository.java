package com.freskoexpress.api.domain.inventario;

import com.freskoexpress.api.shared.enums.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaAndActivoTrue(CategoriaProducto categoria);

    @Query(value = "SELECT p.* FROM producto p WHERE p.activo = true " +
        "AND (SELECT COALESCE(SUM(l.cantidad_actual),0) FROM lote l " +
        "WHERE l.id_producto = p.id_producto AND l.estado = 'activo') < p.stock_minimo",
        nativeQuery = true)
    List<Producto> findProductosBajoStockMinimo();
}
