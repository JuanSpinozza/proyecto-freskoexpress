package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.api.domain.auth.Usuario;
import com.freskoexpress.api.domain.auth.UsuarioRepository;
import com.freskoexpress.api.domain.pedido.dto.*;
import com.freskoexpress.api.shared.enums.RolUsuario;
import com.freskoexpress.api.shared.exception.BusinessException;
import com.freskoexpress.api.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public ClienteResponse crear(CrearClienteRequest request) {
        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado: " + request.idUsuario()));

        if (usuario.getRol() != RolUsuario.cliente) {
            throw new BusinessException(
                    "El usuario debe tener rol 'cliente' para ser registrado como cliente corporativo");
        }
        if (clienteRepository.findByUsuarioIdUsuario(request.idUsuario()).isPresent()) {
            throw new BusinessException(
                    "El usuario " + request.idUsuario() + " ya tiene un perfil de cliente asociado");
        }
        if (clienteRepository.existsByNit(request.nit())) {
            throw new BusinessException(
                    "Ya existe un cliente registrado con NIT: " + request.nit());
        }
        if ((request.coordenadaLat() != null && request.coordenadaLng() == null)
                || (request.coordenadaLat() == null && request.coordenadaLng() != null)) {
            throw new BusinessException(
                    "coordenada_lat y coordenada_lng deben proporcionarse juntas o ninguna");
        }

        Cliente cliente = Cliente.builder()
                .usuario(usuario)
                .razonSocial(request.razonSocial())
                .nit(request.nit())
                .tipo(request.tipo())
                .direccion(request.direccion())
                .ciudad(request.ciudad())
                .coordenadaLat(request.coordenadaLat())
                .coordenadaLng(request.coordenadaLng())
                .build();

        return ClienteResponse.from(clienteRepository.save(cliente));
    }

    public ClienteResponse obtener(Integer id) {
        return clienteRepository.findById(id)
                .map(ClienteResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado: " + id));
    }

    public ClienteResponse obtenerPorUsuario(Integer idUsuario) {
        return clienteRepository.findByUsuarioIdUsuario(idUsuario)
                .map(ClienteResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe perfil de cliente para el usuario: " + idUsuario));
    }

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll()
                .stream().map(ClienteResponse::from).toList();
    }

    @Transactional
    public ClienteResponse actualizarCoordenadas(Integer id,
                                                 java.math.BigDecimal lat,
                                                 java.math.BigDecimal lng) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado: " + id));
        cliente.setCoordenadaLat(lat);
        cliente.setCoordenadaLng(lng);
        return ClienteResponse.from(clienteRepository.save(cliente));
    }
}