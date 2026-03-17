package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.api.domain.inventario.Lote;
import com.freskoexpress.api.domain.inventario.LoteRepository;
import com.freskoexpress.api.domain.inventario.ProductoRepository;
import com.freskoexpress.api.domain.pedido.dto.*;
import com.freskoexpress.api.domain.pedido.state.PedidoState;
import com.freskoexpress.api.domain.pedido.state.PedidoStateResolver;
import com.freskoexpress.api.shared.enums.EstadoPedido;
import com.freskoexpress.api.shared.exception.BusinessException;
import com.freskoexpress.api.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository    pedidoRepository;
    private final ClienteRepository   clienteRepository;
    private final LoteRepository      loteRepository;
    private final ProductoRepository  productoRepository;
    private final PedidoStateResolver stateResolver;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         LoteRepository loteRepository,
                         ProductoRepository productoRepository,
                         PedidoStateResolver stateResolver) {
        this.pedidoRepository   = pedidoRepository;
        this.clienteRepository  = clienteRepository;
        this.loteRepository     = loteRepository;
        this.productoRepository = productoRepository;
        this.stateResolver      = stateResolver;
    }

    @Transactional
    public PedidoResponse crearPedido(CrearPedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.idCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .fechaEntregaReq(request.fechaEntregaReq())
                .ventanaInicio(request.ventanaInicio())
                .ventanaFin(request.ventanaFin())
                .estado(EstadoPedido.confirmado)
                .build();

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequest item : request.items()) {
            // FIFO: lote más antiguo con stock suficiente
            List<Lote> lotesFIFO = loteRepository.findActivosByProductoFIFO(item.idProducto());
            if (lotesFIFO.isEmpty()) {
                throw new BusinessException("Sin stock para producto: " + item.idProducto());
            }
            Lote lote = lotesFIFO.get(0);
            if (lote.getCantidadActual().compareTo(item.cantidad()) < 0) {
                throw new BusinessException("Stock insuficiente en lote: " + lote.getNumeroLote());
            }
            lote.setCantidadActual(lote.getCantidadActual().subtract(item.cantidad()));
            loteRepository.save(lote);

            BigDecimal precio = lote.getProducto().getPrecioUnitario();
            detalles.add(DetallePedido.builder()
                    .id(new DetallePedidoId())
                    .pedido(pedido)
                    .lote(lote)
                    .cantidad(item.cantidad())
                    .precioUnitario(precio)
                    .build());
            total = total.add(precio.multiply(item.cantidad()));
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(total);
        return PedidoResponse.from(pedidoRepository.save(pedido));
    }

    /**
     * Usa el State Pattern para ejecutar la transición.
     * No hay ningún if/switch aquí — el estado actual sabe qué hacer.
     */
    @Transactional
    public PedidoResponse avanzarEstado(Integer idPedido, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + idPedido));

        PedidoState estadoActual = stateResolver.resolve(pedido.getEstado());

        switch (nuevoEstado) {
            case preparacion -> estadoActual.aPreparacion(pedido);
            case en_ruta     -> estadoActual.aEnRuta(pedido);
            case entregado   -> estadoActual.aEntregado(pedido);
            case fallido     -> estadoActual.aFallido(pedido);
            default -> throw new BusinessException(
                    "Transición de estado no permitida: " + nuevoEstado);
        }

        return PedidoResponse.from(pedidoRepository.save(pedido));
    }

    public List<PedidoResponse> listarPorCliente(Integer idCliente) {
        return pedidoRepository.findByClienteIdClienteOrderByFechaPedidoDesc(idCliente)
                .stream().map(PedidoResponse::from).toList();
    }

    public List<PedidoResponse> listarPendientesSinRuta() {
        return pedidoRepository.findByEstadoAndIdRutaIsNull(EstadoPedido.confirmado)
                .stream().map(PedidoResponse::from).toList();
    }
}