package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.pedido.PedidoService;
import com.freskoexpress.api.domain.pedido.dto.*;
import com.freskoexpress.api.shared.enums.EstadoPedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Ciclo de vida del pedido con State Pattern")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'CLIENTE')")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Operation(summary = "Crear pedido — asignación FIFO automática de lotes")
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody CrearPedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(request));
    }

    @GetMapping("/cliente/{idCliente}")
    @Operation(summary = "Historial de pedidos de un cliente")
    public ResponseEntity<List<PedidoResponse>> porCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(pedidoService.listarPorCliente(idCliente));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    @GetMapping("/sin-ruta")
    @Operation(summary = "Pedidos confirmados pendientes de asignación a ruta")
    public ResponseEntity<List<PedidoResponse>> sinRuta() {
        return ResponseEntity.ok(pedidoService.listarPendientesSinRuta());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Avanzar estado del pedido — validado por State Pattern")
    public ResponseEntity<PedidoResponse> avanzarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.avanzarEstado(id, estado));
    }
}