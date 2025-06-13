package com.telconova.support_backend.repository;

import com.telconova.support_backend.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    // List<Orden> findByEstado(String estado);
    // Buscar por estado (nombre), clienteId o tipoId
    java.util.List<Orden> findByCliente_Id(Long id);
    java.util.List<Orden> findByTipo_Id(Long id);
    java.util.List<Orden> findByUsuarioId(Long usuarioId);
    java.util.List<Orden> findByEstadoNombre(String estado);
    java.util.Optional<Orden> findTopByOrderByIdDesc();
}
