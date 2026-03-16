package com.freskoexpress.api.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByUsuarioIdUsuario(Integer idUsuario);
    boolean existsByNit(String nit);
}
