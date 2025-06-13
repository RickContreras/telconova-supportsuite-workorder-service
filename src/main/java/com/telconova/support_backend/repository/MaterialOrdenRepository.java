package com.telconova.support_backend.repository;

import com.telconova.support_backend.entity.MaterialOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialOrdenRepository extends JpaRepository<MaterialOrden, Long> {
    List<MaterialOrden> findByOrdenId(Long ordenId);
}