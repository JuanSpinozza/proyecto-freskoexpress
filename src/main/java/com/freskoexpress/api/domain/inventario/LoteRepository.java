package com.freskoexpress.api.domain.inventario;

import com.freskoexpress.shared.enums.EstadoLote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {

    // FIFO: lotes activos ordenados por fecha_ingreso ASC
    @Query("SELECT l FROM Lote l WHERE l.producto.idProducto = :idProducto " +
           "AND l.estado = com.freskoexpress.shared.enums.EstadoLote.activo " +
           "ORDER BY l.fechaIngreso ASC")
    List<Lote> findActivosByProductoFIFO(@Param("idProducto") Integer idProducto);

    @Query("SELECT l FROM Lote l WHERE l.estado = com.freskoexpress.shared.enums.EstadoLote.activo " +
           "AND l.fechaVencimiento <= :fechaLimite ORDER BY l.fechaVencimiento ASC")
    List<Lote> findLotesProximosAVencer(@Param("fechaLimite") LocalDate fechaLimite);

    List<Lote> findByProductoIdProductoAndEstado(Integer idProducto, EstadoLote estado);
}
