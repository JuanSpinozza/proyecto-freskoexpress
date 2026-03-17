package com.freskoexpress.api.domain.proveedor;

import com.freskoexpress.api.domain.auth.Usuario;
import com.freskoexpress.api.domain.auth.UsuarioRepository;
import com.freskoexpress.api.domain.proveedor.dto.*;
import com.freskoexpress.api.domain.proveedor.validation.ProveedorValidationChain;
import com.freskoexpress.api.shared.enums.EstadoProveedor;
import com.freskoexpress.api.shared.exception.BusinessException;
import com.freskoexpress.api.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository      proveedorRepository;
    private final UsuarioRepository        usuarioRepository;
    private final ProveedorValidationChain validationChain;  // Chain of Responsibility

    public ProveedorService(ProveedorRepository proveedorRepository,
                            UsuarioRepository usuarioRepository,
                            ProveedorValidationChain validationChain) {
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository   = usuarioRepository;
        this.validationChain     = validationChain;
    }

    @Transactional
    public ProveedorResponse registrar(CrearProveedorRequest request) {
        // Ejecuta todos los eslabones de validación
        validationChain.validate(request);

        Proveedor proveedor = Proveedor.builder()
                .nit(request.nit())
                .razonSocial(request.razonSocial())
                .contactoCorreo(request.contactoCorreo())
                .contactoTelefono(request.contactoTelefono())
                .capacidadSemanal(request.capacidadSemanal())
                .estado(EstadoProveedor.pendiente)
                .build();

        return ProveedorResponse.from(proveedorRepository.save(proveedor));
    }

    @Transactional
    public ProveedorResponse revisar(Integer idProveedor, Integer idRevisor,
                                     RevisionProveedorRequest request) {
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Proveedor no encontrado: " + idProveedor));

        if (request.nuevoEstado() == EstadoProveedor.rechazado
                && (request.razonRechazo() == null || request.razonRechazo().isBlank())) {
            throw new BusinessException(
                    "La razón de rechazo es obligatoria al rechazar un proveedor");
        }

        Usuario revisor = usuarioRepository.findById(idRevisor)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Revisor no encontrado: " + idRevisor));

        proveedor.setEstado(request.nuevoEstado());
        proveedor.setRazonRechazo(request.razonRechazo());
        proveedor.setUsuarioRevisor(revisor);

        return ProveedorResponse.from(proveedorRepository.save(proveedor));
    }

    public List<ProveedorResponse> listarPorEstado(EstadoProveedor estado) {
        return proveedorRepository.findByEstado(estado)
                .stream().map(ProveedorResponse::from).toList();
    }

    public ProveedorResponse obtener(Integer id) {
        return proveedorRepository.findById(id)
                .map(ProveedorResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Proveedor no encontrado: " + id));
    }
}