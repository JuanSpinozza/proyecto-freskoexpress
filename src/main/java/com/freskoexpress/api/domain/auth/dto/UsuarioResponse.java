package com.freskoexpress.api.domain.auth.dto;

import com.freskoexpress.api.domain.auth.Usuario;
import com.freskoexpress.api.shared.enums.RolUsuario;

import java.time.OffsetDateTime;

public record UsuarioResponse(
    Integer idUsuario,
    String nombre,
    String correo,
    RolUsuario rol,
    Boolean activo,
    OffsetDateTime fechaCreacion
) {
    public static UsuarioResponse from(Usuario u) {
        return new UsuarioResponse(u.getIdUsuario(), u.getNombre(),
                u.getCorreo(), u.getRol(), u.getActivo(), u.getFechaCreacion());
    }
}
