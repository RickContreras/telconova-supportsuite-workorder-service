package com.telconova.support_backend.dto;

public class MaterialOrdenDTO {
    private String nombreMaterial;
    private Integer cantidad;

    public String getNombreMaterial() { return nombreMaterial; }
    public void setNombreMaterial(String nombreMaterial) { this.nombreMaterial = nombreMaterial; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}