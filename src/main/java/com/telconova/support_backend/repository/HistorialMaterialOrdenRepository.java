package com.telconova.support_backend.repository;

import com.telconova.support_backend.entity.HistorialMaterialOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialMaterialOrdenRepository extends JpaRepository<HistorialMaterialOrden, Long> {
    List<HistorialMaterialOrden> findByMaterialOrdenIdOrderByFechaModificacionDesc(Long materialOrdenId);
}