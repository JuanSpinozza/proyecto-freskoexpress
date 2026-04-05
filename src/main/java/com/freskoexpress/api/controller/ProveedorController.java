package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.proveedor.ProveedorService;
import com.freskoexpress.api.domain.proveedor.dto.*;
import com.freskoexpress.api.shared.enums.EstadoProveedor;
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
@RequestMapping("/api/v1/proveedores")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Proveedores", description = "Registro y gestión de proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    @Operation(summary = "Registrar proveedor — ejecuta cadena de validación")
    public ResponseEntity<ProveedorResponse> registrar(@Valid @RequestBody CrearProveedorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.registrar(request));
    }

    // @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID")
    public ResponseEntity<ProveedorResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.obtener(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Listar proveedores por estado")
    public ResponseEntity<List<ProveedorResponse>> listar(
            @RequestParam(defaultValue = "pendiente") EstadoProveedor estado) {
        return ResponseEntity.ok(proveedorService.listarPorEstado(estado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/revision")
    @Operation(summary = "Aprobar o rechazar proveedor (admin)")
    public ResponseEntity<ProveedorResponse> revisar(
            @PathVariable Integer id,
            @RequestParam Integer idRevisor,
            @Valid @RequestBody RevisionProveedorRequest request) {
        return ResponseEntity.ok(proveedorService.revisar(id, idRevisor, request));
    }
}
