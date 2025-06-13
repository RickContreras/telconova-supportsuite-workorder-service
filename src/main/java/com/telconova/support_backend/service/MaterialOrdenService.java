package com.telconova.support_backend.service;

import com.telconova.support_backend.dto.MaterialOrdenDTO;
import com.telconova.support_backend.entity.MaterialOrden;
import com.telconova.support_backend.entity.Material;
import com.telconova.support_backend.entity.Orden;
import com.telconova.support_backend.repository.MaterialOrdenRepository;
import com.telconova.support_backend.repository.MaterialRepository;
import com.telconova.support_backend.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MaterialOrdenService {

    @Autowired
    private MaterialOrdenRepository materialOrdenRepository;
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private MaterialRepository materialRepository;

    public MaterialOrden registrarMaterialOrden(MaterialOrdenDTO input) {
        // Crear material solo con el nombre
        Material material = new Material();
        material.setNombre(input.getNombreMaterial());
        material = materialRepository.save(material);

        // Obtener la última orden creada
        Orden orden = ordenRepository.findTopByOrderByIdDesc()
            .orElseThrow(() -> new RuntimeException("No hay órdenes registradas"));

        // Crear MaterialOrden
        MaterialOrden materialOrden = new MaterialOrden();
        materialOrden.setOrden(orden);
        materialOrden.setMaterial(material);
        materialOrden.setCantidad(input.getCantidad() != null ? input.getCantidad() : 1);
        materialOrden.setFechaUso(LocalDateTime.now());

        return materialOrdenRepository.save(materialOrden);
    }
}