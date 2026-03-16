package com.freskoexpress.api.controller;

import com.freskoexpress.domain.auth.AuthService;
import com.freskoexpress.domain.auth.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Autenticación y gestión de usuarios")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión — retorna JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
    }

    @GetMapping("/conductores")
    @Operation(summary = "Listar conductores activos")
    public ResponseEntity<List<UsuarioResponse>> conductores() {
        return ResponseEntity.ok(authService.listarConductores());
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar usuario (soft delete)")
    public ResponseEntity<Void> desactivar(@PathVariable Integer id) {
        authService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
