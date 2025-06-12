package com.telconova.support_backend.repository;

import com.telconova.support_backend.entity.MaterialOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialOrdenRepository extends JpaRepository<MaterialOrden, Long> {
}