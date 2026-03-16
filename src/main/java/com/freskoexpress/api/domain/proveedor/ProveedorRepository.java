package com.freskoexpress.api.domain.proveedor;

import com.freskoexpress.shared.enums.EstadoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findByNit(String nit);
    boolean existsByNit(String nit);
    List<Proveedor> findByEstado(EstadoProveedor estado);

    @Query("SELECT p FROM Proveedor p LEFT JOIN FETCH p.certificados WHERE p.idProveedor = :id")
    Optional<Proveedor> findByIdWithCertificados(@Param("id") Integer id);
}
