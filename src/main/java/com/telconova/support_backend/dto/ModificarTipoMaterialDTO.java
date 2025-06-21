package com.telconova.support_backend.dto;

public class ModificarTipoMaterialDTO {
    private Long materialOrdenId;
    private Long nuevoMaterialId;
    private String justificacion;

    public Long getMaterialOrdenId() { return materialOrdenId; }
    public void setMaterialOrdenId(Long materialOrdenId) { this.materialOrdenId = materialOrdenId; }
    
    public Long getNuevoMaterialId() { return nuevoMaterialId; }
    public void setNuevoMaterialId(Long nuevoMaterialId) { this.nuevoMaterialId = nuevoMaterialId; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }
}