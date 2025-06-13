package com.telconova.support_backend.dto;

public class ModificarCantidadMaterialDTO {
    private Long materialOrdenId;
    private Integer nuevaCantidad;
    private String justificacion;
    private String usuarioModificacion;

    public Long getMaterialOrdenId() { return materialOrdenId; }
    public void setMaterialOrdenId(Long materialOrdenId) { this.materialOrdenId = materialOrdenId; }
    
    public Integer getNuevaCantidad() { return nuevaCantidad; }
    public void setNuevaCantidad(Integer nuevaCantidad) { this.nuevaCantidad = nuevaCantidad; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

}