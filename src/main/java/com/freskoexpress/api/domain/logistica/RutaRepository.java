package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.shared.enums.EstadoRuta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    List<Ruta> findByFechaRuta(LocalDate fecha);
    List<Ruta> findByEstado(EstadoRuta estado);
    List<Ruta> findByConductorIdUsuario(Integer idConductor);
}
