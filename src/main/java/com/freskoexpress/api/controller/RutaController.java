package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.logistica.RutaService;
import com.freskoexpress.api.domain.logistica.dto.*;
import com.freskoexpress.api.shared.enums.EstadoRuta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rutas")
@Tag(name = "Rutas", description = "Planificación de rutas con Facade Pattern")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @PostMapping
    @Operation(summary = "Planificar ruta — orquestado por PlanificacionFacade")
    public ResponseEntity<RutaResponse> planificar(@Valid @RequestBody CrearRutaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rutaService.crearRuta(request));
    }

    @GetMapping
    @Operation(summary = "Listar rutas por fecha")
    public ResponseEntity<List<RutaResponse>> porFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(rutaService.listarPorFecha(fecha));
    }

    @GetMapping("/conductor/{id}")
    @Operation(summary = "Rutas asignadas a un conductor")
    public ResponseEntity<List<RutaResponse>> porConductor(@PathVariable Integer id) {
        return ResponseEntity.ok(rutaService.listarPorConductor(id));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de ruta")
    public ResponseEntity<RutaResponse> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoRuta estado) {
        return ResponseEntity.ok(rutaService.actualizarEstado(id, estado));
    }
}