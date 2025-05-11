package com.telconova.support_backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @GetMapping
    public String listarOrdenes() {
        return "Lista de órdenes";
    }

    @PostMapping
    public String crearOrden() {
        return "Orden creada";
    }
}