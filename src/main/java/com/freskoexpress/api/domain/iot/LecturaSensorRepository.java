package com.freskoexpress.api.domain.iot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {

    @Query("SELECT l FROM LecturaSensor l " +
           "WHERE l.vehiculo.idVehiculo = :idVehiculo AND l.fueraDeRango = true " +
           "ORDER BY l.timestamp DESC")
    List<LecturaSensor> findFueraDeRangoByVehiculo(@Param("idVehiculo") Integer idVehiculo);

    List<LecturaSensor> findByVehiculoIdVehiculoOrderByTimestampDesc(Integer idVehiculo);
}
