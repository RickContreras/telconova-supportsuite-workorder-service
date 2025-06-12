package com.telconova.support_backend.dto;

public class RegistrarMaterialOrdenInput {
    private String comentario;
    private Integer cantidad;

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}