package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.shared.enums.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo> findByDisponibleTrueAndCiudad(String ciudad);
    List<Vehiculo> findByTipoAndDisponibleTrue(TipoVehiculo tipo);
}
