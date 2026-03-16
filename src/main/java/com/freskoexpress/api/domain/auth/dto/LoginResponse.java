package com.freskoexpress.api.domain.auth.dto;

import com.freskoexpress.shared.enums.RolUsuario;

public record LoginResponse(
    String token,
    String tipo,
    Integer idUsuario,
    String nombre,
    RolUsuario rol
) {}
