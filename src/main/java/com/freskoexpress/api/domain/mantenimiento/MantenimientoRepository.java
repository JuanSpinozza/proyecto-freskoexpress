package com.freskoexpress.api.domain.mantenimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {
    List<Mantenimiento> findByVehiculoIdVehiculoOrderByFechaServicioDesc(Integer idVehiculo);

    // Vehículos próximos a mantenimiento por km
    @Query("SELECT m FROM Mantenimiento m " +
           "WHERE m.proxKm IS NOT NULL AND m.vehiculo.kilometraje >= (m.proxKm - :margen)")
    List<Mantenimiento> findProximosPorKm(@Param("margen") Integer margen);

    // Vehículos próximos a mantenimiento por fecha
    @Query("SELECT m FROM Mantenimiento m " +
           "WHERE m.proxFecha IS NOT NULL AND m.proxFecha <= :fechaLimite")
    List<Mantenimiento> findProximosPorFecha(@Param("fechaLimite") LocalDate fechaLimite);
}
