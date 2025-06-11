package com.telconova.support_backend.controller;

import com.telconova.support_backend.entity.TipoEstado;
import com.telconova.support_backend.repository.TipoEstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadosorden")
public class TipoEstadoController {

    @Autowired
    private TipoEstadoRepository tipoEstadoRepository;

    @GetMapping
    public List<TipoEstado> listarTodos() {
        return tipoEstadoRepository.findAll();
    }
}