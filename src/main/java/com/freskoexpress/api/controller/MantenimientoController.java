package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.mantenimiento.MantenimientoService;
import com.freskoexpress.api.domain.mantenimiento.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mantenimientos")
@Tag(name = "Mantenimiento", description = "Historial y alertas predictivas de flota")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    public MantenimientoController(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @PostMapping
    @Operation(summary = "Registrar mantenimiento realizado")
    public ResponseEntity<MantenimientoResponse> registrar(
            @Valid @RequestBody CrearMantenimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mantenimientoService.registrar(request));
    }

    @GetMapping("/vehiculo/{id}")
    @Operation(summary = "Historial de mantenimientos de un vehículo")
    public ResponseEntity<List<MantenimientoResponse>> historial(@PathVariable Integer id) {
        return ResponseEntity.ok(mantenimientoService.historialPorVehiculo(id));
    }
}