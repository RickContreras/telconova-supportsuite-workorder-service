package com.telconova.support_backend.service;

import com.telconova.support_backend.entity.Material;
import com.telconova.support_backend.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> listarMateriales() {
        return materialRepository.findAll();
    }
}