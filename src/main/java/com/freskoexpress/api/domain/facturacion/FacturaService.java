package com.freskoexpress.api.domain.facturacion;

import com.freskoexpress.domain.facturacion.dto.*;
import com.freskoexpress.domain.facturacion.strategy.PagoContext;
import com.freskoexpress.domain.pedido.Pedido;
import com.freskoexpress.domain.pedido.PedidoRepository;
import com.freskoexpress.shared.enums.EstadoFactura;
import com.freskoexpress.shared.exception.BusinessException;
import com.freskoexpress.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final PedidoRepository  pedidoRepository;
    private final PagoContext       pagoContext;        // Strategy Pattern

    @Transactional
    public FacturaResponse generarFactura(Integer idPedido) {
        if (facturaRepository.findByPedidoIdPedido(idPedido).isPresent()) {
            throw new BusinessException("El pedido " + idPedido + " ya tiene factura generada");
        }
        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + idPedido));

        int secuencia = facturaRepository.findMaxSecuencia() + 1;
        String numero = String.format("FV-%06d", secuencia);

        Factura factura = Factura.builder()
            .pedido(pedido)
            .numeroFactura(numero)
            .total(pedido.getTotal())
            .estado(EstadoFactura.pendiente)
            .build();

        return FacturaResponse.from(facturaRepository.save(factura));
    }

    @Transactional
    public FacturaResponse registrarPago(Integer idFactura, RegistrarPagoRequest request) {
        Factura factura = facturaRepository.findById(idFactura)
            .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada: " + idFactura));

        if (factura.getEstado() == EstadoFactura.pagada) {
            throw new BusinessException("La factura ya fue pagada");
        }

        // Delega al Strategy correcto sin conocer cuál es
        String referencia = pagoContext.ejecutar(request.metodoPago(), request);

        factura.setMetodoPago(request.metodoPago());
        factura.setReferenciaPago(referencia);
        factura.setEstado(EstadoFactura.pagada);
        factura.setFechaPago(OffsetDateTime.now());

        return FacturaResponse.from(facturaRepository.save(factura));
    }

    public FacturaResponse obtenerPorPedido(Integer idPedido) {
        return facturaRepository.findByPedidoIdPedido(idPedido)
            .map(FacturaResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Factura no encontrada para pedido: " + idPedido));
    }
}
