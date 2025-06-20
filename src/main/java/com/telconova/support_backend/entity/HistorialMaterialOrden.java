package com.telconova.support_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_material_orden")
public class HistorialMaterialOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "material_orden_id", nullable = false)
    private MaterialOrden materialOrden;

    @Column(name = "campo_modificado", nullable = false, length = 100)
    private String campoModificado;

    @Column(name = "valor_anterior", length = 255)
    private String valorAnterior;

    @Column(name = "valor_nuevo", nullable = false, length = 255)
    private String valorNuevo;

    @Column(name = "justificacion", nullable = false, columnDefinition = "TEXT")
    private String justificacion;

    @Column(name = "usuario_modificacion", nullable = false, length = 100)
    private String usuarioModificacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    // Constructor vacío
    public HistorialMaterialOrden() {
        this.fechaModificacion = LocalDateTime.now();
    }

    // Constructor con parámetros principales
    public HistorialMaterialOrden(MaterialOrden materialOrden, String campoModificado, 
                                 String valorAnterior, String valorNuevo, String justificacion, 
                                 String usuarioModificacion) {
        this.materialOrden = materialOrden;
        this.campoModificado = campoModificado;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.justificacion = justificacion;
        this.usuarioModificacion = usuarioModificacion;
        this.fechaModificacion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MaterialOrden getMaterialOrden() { return materialOrden; }
    public void setMaterialOrden(MaterialOrden materialOrden) { this.materialOrden = materialOrden; }

    public String getCampoModificado() { return campoModificado; }
    public void setCampoModificado(String campoModificado) { this.campoModificado = campoModificado; }

    public String getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(String valorAnterior) { this.valorAnterior = valorAnterior; }

    public String getValorNuevo() { return valorNuevo; }
    public void setValorNuevo(String valorNuevo) { this.valorNuevo = valorNuevo; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public String getUsuarioModificacion() { return usuarioModificacion; }
    public void setUsuarioModificacion(String usuarioModificacion) { this.usuarioModificacion = usuarioModificacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
}