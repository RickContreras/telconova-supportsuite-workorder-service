package com.telconova.support_backend.service;

import com.telconova.support_backend.entity.MaterialOrden;
import com.telconova.support_backend.repository.MaterialOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialOrdenService {

    @Autowired
    private MaterialOrdenRepository materialOrdenRepository;

    public MaterialOrden registrarMaterialOrden(String comentario, Integer cantidad) {
        MaterialOrden materialOrden = new MaterialOrden();
        materialOrden.setComentario(comentario);
        materialOrden.setCantidad(cantidad);
        return materialOrdenRepository.save(materialOrden);
    }
}