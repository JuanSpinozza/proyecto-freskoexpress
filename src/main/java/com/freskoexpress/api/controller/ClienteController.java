package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.pedido.ClienteService;
import com.freskoexpress.api.domain.pedido.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes corporativos")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'CLIENTE')")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Registrar perfil de cliente corporativo para un usuario existente")
    public ResponseEntity<ClienteResponse> crear(
            @Valid @RequestBody CrearClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes corporativos")
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public ResponseEntity<ClienteResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.obtener(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener cliente por ID de usuario")
    public ResponseEntity<ClienteResponse> porUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(clienteService.obtenerPorUsuario(idUsuario));
    }

    @PatchMapping("/{id}/coordenadas")
    @Operation(summary = "Actualizar coordenadas GPS del cliente para optimización de rutas")
    public ResponseEntity<ClienteResponse> actualizarCoordenadas(
            @PathVariable Integer id,
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng) {
        return ResponseEntity.ok(clienteService.actualizarCoordenadas(id, lat, lng));
    }
}