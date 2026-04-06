// VehiculoService.java
package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.api.domain.logistica.dto.*;
import com.freskoexpress.api.shared.exception.BusinessException;
import com.freskoexpress.api.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional
    public VehiculoResponse crear(CrearVehiculoRequest request) {
        if (vehiculoRepository.findByPlaca(request.placa()).isPresent()) {
            throw new BusinessException(
                    "Ya existe un vehículo registrado con la placa: " + request.placa());
        }
        Vehiculo vehiculo = Vehiculo.builder()
                .placa(request.placa())
                .tipo(request.tipo())
                .marca(request.marca())
                .modelo(request.modelo())
                .capacidadKg(request.capacidadKg())
                .ciudad(request.ciudad())
                .disponible(true)
                .kilometraje(0)
                .build();
        return VehiculoResponse.from(vehiculoRepository.save(vehiculo));
    }

    public List<VehiculoResponse> listarTodos() {
        return vehiculoRepository.findAll()
                .stream().map(VehiculoResponse::from).toList();
    }

    public List<VehiculoResponse> listarDisponibles(String ciudad) {
        return vehiculoRepository.findByDisponibleTrueAndCiudad(ciudad)
                .stream().map(VehiculoResponse::from).toList();
    }

    public VehiculoResponse obtener(Integer id) {
        return vehiculoRepository.findById(id)
                .map(VehiculoResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehículo no encontrado: " + id));
    }

    @Transactional
    public VehiculoResponse actualizarKilometraje(Integer id, Integer kmRecorridos) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehículo no encontrado: " + id));
        if (kmRecorridos <= 0) {
            throw new BusinessException(
                    "Los km recorridos deben ser mayores a 0");
        }
        vehiculo.setKilometraje(vehiculo.getKilometraje() + kmRecorridos);
        return VehiculoResponse.from(vehiculoRepository.save(vehiculo));
    }

    @Transactional
    public VehiculoResponse actualizarDisponibilidad(Integer id, Boolean disponible) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehículo no encontrado: " + id));
        vehiculo.setDisponible(disponible);
        return VehiculoResponse.from(vehiculoRepository.save(vehiculo));
    }
}