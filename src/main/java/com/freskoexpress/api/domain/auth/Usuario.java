package com.freskoexpress.api.domain.auth;

import com.freskoexpress.api.shared.enums.RolUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "contrasena_hash", nullable = false, length = 255)
    private String contrasenaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;

    @Column(name = "mfa_habilitado", nullable = false)
    private Boolean mfaHabilitado = false;

    @Column(length = 30)
    private String licencia;

    @Column(name = "tipo_licencia", length = 10)
    private String tipoLicencia;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    // ── Constructores ─────────────────────────────────────────────────
    protected Usuario() {}   // requerido por JPA

    private Usuario(Builder builder) {
        this.nombre         = builder.nombre;
        this.correo         = builder.correo;
        this.contrasenaHash = builder.contrasenaHash;
        this.rol            = builder.rol;
        this.mfaHabilitado  = builder.mfaHabilitado;
        this.licencia       = builder.licencia;
        this.tipoLicencia   = builder.tipoLicencia;
        this.activo         = builder.activo;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = OffsetDateTime.now();
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String     nombre;
        private String     correo;
        private String     contrasenaHash;
        private RolUsuario rol;
        private Boolean    mfaHabilitado = false;
        private String     licencia;
        private String     tipoLicencia;
        private Boolean    activo = true;

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder contrasenaHash(String contrasenaHash) {
            this.contrasenaHash = contrasenaHash;
            return this;
        }

        public Builder rol(RolUsuario rol) {
            this.rol = rol;
            return this;
        }

        public Builder mfaHabilitado(Boolean mfaHabilitado) {
            this.mfaHabilitado = mfaHabilitado;
            return this;
        }

        public Builder licencia(String licencia) {
            this.licencia = licencia;
            return this;
        }

        public Builder tipoLicencia(String tipoLicencia) {
            this.tipoLicencia = tipoLicencia;
            return this;
        }

        public Builder activo(Boolean activo) {
            this.activo = activo;
            return this;
        }

        public Usuario build() {
            if (correo == null || correo.isBlank()) {
                throw new IllegalStateException("El correo es obligatorio");
            }
            if (rol == null) {
                throw new IllegalStateException("El rol es obligatorio");
            }
            if (contrasenaHash == null || contrasenaHash.isBlank()) {
                throw new IllegalStateException("La contraseña es obligatoria");
            }
            return new Usuario(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer       getIdUsuario()     { return idUsuario; }
    public String        getNombre()        { return nombre; }
    public String        getCorreo()        { return correo; }
    public String        getContrasenaHash(){ return contrasenaHash; }
    public RolUsuario    getRol()           { return rol; }
    public Boolean       getMfaHabilitado() { return mfaHabilitado; }
    public String        getLicencia()      { return licencia; }
    public String        getTipoLicencia()  { return tipoLicencia; }
    public Boolean       getActivo()        { return activo; }
    public OffsetDateTime getFechaCreacion(){ return fechaCreacion; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdUsuario(Integer idUsuario)           { this.idUsuario = idUsuario; }
    public void setNombre(String nombre)                  { this.nombre = nombre; }
    public void setCorreo(String correo)                  { this.correo = correo; }
    public void setContrasenaHash(String contrasenaHash)  { this.contrasenaHash = contrasenaHash; }
    public void setRol(RolUsuario rol)                    { this.rol = rol; }
    public void setMfaHabilitado(Boolean mfaHabilitado)   { this.mfaHabilitado = mfaHabilitado; }
    public void setLicencia(String licencia)              { this.licencia = licencia; }
    public void setTipoLicencia(String tipoLicencia)      { this.tipoLicencia = tipoLicencia; }
    public void setActivo(Boolean activo)                 { this.activo = activo; }
    public void setFechaCreacion(OffsetDateTime f)        { this.fechaCreacion = f; }

    // ── UserDetails ───────────────────────────────────────────────────
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name().toUpperCase()));
    }

    @Override public String  getPassword()                 { return contrasenaHash; }
    @Override public String  getUsername()                 { return correo; }
    @Override public boolean isEnabled()                   { return activo; }
    @Override public boolean isAccountNonExpired()         { return true; }
    @Override public boolean isAccountNonLocked()          { return true; }
    @Override public boolean isCredentialsNonExpired()     { return true; }
}