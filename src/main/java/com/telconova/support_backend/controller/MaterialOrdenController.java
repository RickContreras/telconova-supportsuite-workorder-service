package com.telconova.support_backend.controller;

import com.telconova.support_backend.dto.MaterialOrdenDTO;
import com.telconova.support_backend.dto.ModificarCantidadMaterialDTO;
import com.telconova.support_backend.entity.MaterialOrden;
import com.telconova.support_backend.entity.HistorialMaterialOrden;
import com.telconova.support_backend.service.MaterialOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MaterialOrdenController {

    @Autowired
    private MaterialOrdenService materialOrdenService;

    @MutationMapping
    public MaterialOrden registrarMaterialOrden(@Argument("input") MaterialOrdenDTO input) {
        return materialOrdenService.registrarMaterialOrden(input);
    }

    @QueryMapping
    public List<MaterialOrden> obtenerMaterialesDeOrden(@Argument Long ordenId) {
        return materialOrdenService.obtenerMaterialesDeOrden(ordenId);
    }

    @MutationMapping
    public MaterialOrden modificarCantidadMaterial(@Argument("input") ModificarCantidadMaterialDTO input) {
        return materialOrdenService.modificarCantidadMaterial(input);
    }

    @QueryMapping
    public List<HistorialMaterialOrden> obtenerHistorialMaterial(@Argument Long materialOrdenId) {
        return materialOrdenService.obtenerHistorialMaterial(materialOrdenId);
    }
}