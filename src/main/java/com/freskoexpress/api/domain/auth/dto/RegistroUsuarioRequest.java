package com.freskoexpress.api.domain.auth.dto;

import com.freskoexpress.shared.enums.RolUsuario;
import jakarta.validation.constraints.*;

public record RegistroUsuarioRequest(
    @NotBlank @Size(max=100) String nombre,
    @NotBlank @Email @Size(max=150) String correo,
    @NotBlank @Size(min=8) String contrasena,
    @NotNull RolUsuario rol,
    String licencia,
    String tipoLicencia
) {}
