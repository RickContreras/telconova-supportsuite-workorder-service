package com.telconova.support_backend.repository;

import com.telconova.support_backend.entity.TipoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoOrdenRepository extends JpaRepository<TipoOrden, Long> {
}
