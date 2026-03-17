package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.facturacion.FacturaService;
import com.freskoexpress.api.domain.facturacion.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facturas")
@Tag(name = "Facturación", description = "Generación y pago de facturas con Strategy Pattern")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping("/pedido/{idPedido}")
    @Operation(summary = "Generar factura para un pedido confirmado")
    public ResponseEntity<FacturaResponse> generar(@PathVariable Integer idPedido) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facturaService.generarFactura(idPedido));
    }

    @GetMapping("/pedido/{idPedido}")
    @Operation(summary = "Obtener factura de un pedido")
    public ResponseEntity<FacturaResponse> obtener(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(facturaService.obtenerPorPedido(idPedido));
    }

    @PatchMapping("/{idFactura}/pago")
    @Operation(summary = "Registrar pago — selecciona Strategy según método de pago")
    public ResponseEntity<FacturaResponse> pago(
            @PathVariable Integer idFactura,
            @Valid @RequestBody RegistrarPagoRequest request) {
        return ResponseEntity.ok(facturaService.registrarPago(idFactura, request));
    }
}
