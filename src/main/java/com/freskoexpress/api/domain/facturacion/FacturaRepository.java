package com.freskoexpress.api.domain.facturacion;

import com.freskoexpress.api.shared.enums.EstadoFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    Optional<Factura> findByPedidoIdPedido(Integer idPedido);
    boolean existsByNumeroFactura(String numeroFactura);
    List<Factura> findByEstado(EstadoFactura estado);

    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(f.numeroFactura, 4) AS int)), 0) FROM Factura f")
    Integer findMaxSecuencia();
}
