package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.api.shared.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByClienteIdClienteOrderByFechaPedidoDesc(Integer idCliente);
    List<Pedido> findByEstado(EstadoPedido estado);
    List<Pedido> findByEstadoAndIdRutaIsNull(EstadoPedido estado);

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.detalles WHERE p.idPedido = :id")
    Optional<Pedido> findByIdWithDetalles(@Param("id") Integer id);
}
