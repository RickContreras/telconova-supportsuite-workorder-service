package com.telconova.support_backend.controller;

import com.telconova.support_backend.dto.MaterialOrdenDTO;
import com.telconova.support_backend.entity.MaterialOrden;
import com.telconova.support_backend.service.MaterialOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MaterialOrdenController {

    @Autowired
    private MaterialOrdenService materialOrdenService;

    @MutationMapping
    public MaterialOrden registrarMaterialOrden(@Argument("input") MaterialOrdenDTO input) {
        return materialOrdenService.registrarMaterialOrden(input);
    }
}