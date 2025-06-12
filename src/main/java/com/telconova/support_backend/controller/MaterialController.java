package com.telconova.support_backend.controller;

import com.telconova.support_backend.entity.Material;
import com.telconova.support_backend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @QueryMapping
    public List<Material> listarMateriales() {
        return materialService.listarMateriales();
    }
}