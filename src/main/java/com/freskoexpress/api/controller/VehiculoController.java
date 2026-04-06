package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.logistica.VehiculoService;
import com.freskoexpress.api.domain.logistica.dto.*;
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
@RequestMapping("/api/v1/vehiculos")
@Tag(name = "Vehículos", description = "Gestión de flota de vehículos")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo vehículo en la flota")
    public ResponseEntity<VehiculoResponse> crear(
            @Valid @RequestBody CrearVehiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculoService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos los vehículos")
    public ResponseEntity<List<VehiculoResponse>> listar() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar vehículos disponibles por ciudad")
    public ResponseEntity<List<VehiculoResponse>> disponibles(
            @RequestParam String ciudad) {
        return ResponseEntity.ok(vehiculoService.listarDisponibles(ciudad));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vehículo por ID")
    public ResponseEntity<VehiculoResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(vehiculoService.obtener(id));
    }

    @PatchMapping("/{id}/kilometraje")
    @Operation(summary = "Sumar km recorridos al odómetro del vehículo")
    public ResponseEntity<VehiculoResponse> actualizarKilometraje(
            @PathVariable Integer id,
            @RequestParam Integer kmRecorridos) {
        return ResponseEntity.ok(vehiculoService.actualizarKilometraje(id, kmRecorridos));
    }

    @PatchMapping("/{id}/disponibilidad")
    @Operation(summary = "Cambiar disponibilidad del vehículo (manual)")
    public ResponseEntity<VehiculoResponse> actualizarDisponibilidad(
            @PathVariable Integer id,
            @RequestParam Boolean disponible) {
        return ResponseEntity.ok(vehiculoService.actualizarDisponibilidad(id, disponible));
    }
}