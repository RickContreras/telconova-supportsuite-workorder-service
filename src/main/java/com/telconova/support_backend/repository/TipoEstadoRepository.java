package com.telconova.support_backend.repository;

import com.telconova.support_backend.entity.TipoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoEstadoRepository extends JpaRepository<TipoEstado, Long> {
    Optional<TipoEstado> findByNombre(String nombre);
}
