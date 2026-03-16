package com.freskoexpress.api.domain.auth;

import com.freskoexpress.domain.auth.dto.*;
import com.freskoexpress.infra.security.JwtService;
import com.freskoexpress.shared.exception.BusinessException;
import com.freskoexpress.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public LoginResponse login(LoginRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.correo(), request.contrasena())
        );
        Usuario usuario = usuarioRepository.findByCorreo(request.correo())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        String token = jwtService.generateToken(usuario);
        return new LoginResponse(token, "Bearer",
                usuario.getIdUsuario(), usuario.getNombre(), usuario.getRol());
    }

    @Transactional
    public UsuarioResponse registrar(RegistroUsuarioRequest request) {
        if (usuarioRepository.existsByCorreo(request.correo())) {
            throw new BusinessException("El correo ya está registrado");
        }
        Usuario usuario = Usuario.builder()
            .nombre(request.nombre())
            .correo(request.correo())
            .contrasenaHash(passwordEncoder.encode(request.contrasena()))
            .rol(request.rol())
            .licencia(request.licencia())
            .tipoLicencia(request.tipoLicencia())
            .mfaHabilitado(false)
            .activo(true)
            .build();
        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponse> listarConductores() {
        return usuarioRepository.findByRolAndActivoTrue(
            com.freskoexpress.shared.enums.RolUsuario.conductor)
            .stream().map(UsuarioResponse::from).toList();
    }

    @Transactional
    public void desactivar(Integer id) {
        Usuario u = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
        u.setActivo(false);
        usuarioRepository.save(u);
    }
}
