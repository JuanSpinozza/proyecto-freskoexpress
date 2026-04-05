package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.inventario.InventarioService;
import com.freskoexpress.api.domain.inventario.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Inventario", description = "Productos, lotes y control FIFO")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/productos")
    @Operation(summary = "Catálogo completo de productos activos")
    public ResponseEntity<List<ProductoResponse>> catalogo() {
        return ResponseEntity.ok(inventarioService.listarCatalogo());
    }

    @PostMapping("/productos")
    @Operation(summary = "Crear producto en catálogo")
    public ResponseEntity<ProductoResponse> crearProducto(@Valid @RequestBody CrearProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crearProducto(request));
    }

    @PostMapping("/lotes")
    @Operation(summary = "Ingresar nuevo lote al inventario")
    public ResponseEntity<LoteResponse> ingresarLote(@Valid @RequestBody CrearLoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.ingresarLote(request));
    }

    @GetMapping("/lotes/{idProducto}/fifo")
    @Operation(summary = "Ver lotes disponibles ordenados por FIFO (fecha_ingreso ASC)")
    public ResponseEntity<List<LoteResponse>> lotesFIFO(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(inventarioService.lotesFIFO(idProducto));
    }

    @GetMapping("/lotes/proximos-vencer")
    @Operation(summary = "Lotes que vencen en los próximos N días")
    public ResponseEntity<List<LoteResponse>> proxAVencer(
            @RequestParam(defaultValue = "7") int dias) {
        return ResponseEntity.ok(inventarioService.lotesProximosAVencer(dias));
    }

    @GetMapping("/productos/stock-bajo")
    @Operation(summary = "Productos con stock por debajo del mínimo configurado")
    public ResponseEntity<List<ProductoResponse>> stockBajo() {
        return ResponseEntity.ok(inventarioService.productosConStockBajo());
    }
}
