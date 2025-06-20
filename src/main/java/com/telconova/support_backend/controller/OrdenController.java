package com.telconova.support_backend.controller;

import com.telconova.support_backend.entity.Orden;
import com.telconova.support_backend.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @QueryMapping
    public List<Orden> listarOrdenes() {
        return ordenService.listarOrdenes();
    }

    @QueryMapping
    public Orden obtenerOrdenPorId(@Argument Long id) {
        return ordenService.obtenerOrdenPorId(id).orElse(null);
    }

    @QueryMapping
    public List<Orden> filtrarOrdenes(@Argument String estado, @Argument Long clienteId,
            @Argument String fechaInicio, @Argument String fechaCierre, @Argument Long usuarioId) {
        return ordenService.filtrarOrdenes(estado, clienteId, fechaInicio, fechaCierre, usuarioId);
    }

    @MutationMapping
    public Orden cambiarEstadoOrden(@Argument Long id, @Argument String estado) {
        return ordenService.cambiarEstadoOrdenOrThrow(id, estado);
    }

    @QueryMapping
    public List<Orden> obtenerOrdenesPorUsuarioId(@Argument Long usuario_id) {
        return ordenService.obtenerOrdenesPorUsuarioId(usuario_id);
    }

}
