package com.freskoexpress.api.domain.iot;

import com.freskoexpress.api.shared.enums.TipoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    List<Alerta> findByReconocidaFalseOrderByFechaAlertaDesc();
    List<Alerta> findByTipoAndReconocidaFalse(TipoAlerta tipo);
    List<Alerta> findByVehiculoIdVehiculoOrderByFechaAlertaDesc(Integer idVehiculo);
}
