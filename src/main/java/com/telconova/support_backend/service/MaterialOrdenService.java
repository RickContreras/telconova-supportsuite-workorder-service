package com.telconova.support_backend.service;

import com.telconova.support_backend.dto.MaterialOrdenDTO;
import com.telconova.support_backend.dto.ModificarCantidadMaterialDTO;
import com.telconova.support_backend.dto.ModificarTipoMaterialDTO;
import com.telconova.support_backend.entity.MaterialOrden;
import com.telconova.support_backend.entity.Material;
import com.telconova.support_backend.entity.Orden;
import com.telconova.support_backend.entity.HistorialMaterialOrden;
import com.telconova.support_backend.repository.MaterialOrdenRepository;
import com.telconova.support_backend.repository.MaterialRepository;
import com.telconova.support_backend.repository.OrdenRepository;
import com.telconova.support_backend.repository.HistorialMaterialOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaterialOrdenService {

    @Autowired
    private MaterialOrdenRepository materialOrdenRepository;
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private HistorialMaterialOrdenRepository historialMaterialOrdenRepository;

    public MaterialOrden registrarMaterialOrden(MaterialOrdenDTO input) {
        // Crear material con el nombre
        Material material = new Material();
        material.setNombre(input.getNombreMaterial());
        material = materialRepository.save(material);

        // Buscar la orden por ID en lugar de la última creada
        Orden orden = ordenRepository.findById(input.getOrdenId())
            .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + input.getOrdenId()));

        // Crear MaterialOrden
        MaterialOrden materialOrden = new MaterialOrden();
        materialOrden.setOrden(orden);
        materialOrden.setMaterial(material);
        materialOrden.setCantidad(input.getCantidad() != null ? input.getCantidad() : 1);
        materialOrden.setFechaUso(LocalDateTime.now());

        return materialOrdenRepository.save(materialOrden);
    }

    public List<MaterialOrden> obtenerMaterialesDeOrden(Long ordenId) {
        return materialOrdenRepository.findByOrdenId(ordenId);
    }

    @Transactional
    public MaterialOrden modificarCantidadMaterial(ModificarCantidadMaterialDTO input) {
        // Validar justificación obligatoria
        if (input.getJustificacion() == null || input.getJustificacion().trim().isEmpty()) {
            throw new RuntimeException("La justificación es obligatoria para modificar materiales");
        }

        // Buscar el MaterialOrden
        MaterialOrden materialOrden = materialOrdenRepository.findById(input.getMaterialOrdenId())
            .orElseThrow(() -> new RuntimeException("Material de orden no encontrado"));

        // Obtener la orden y validar que no esté finalizada
        Orden orden = materialOrden.getOrden();
        if (orden.getEstado() != null && "Finalizada".equalsIgnoreCase(orden.getEstado().getNombre())) {
            throw new RuntimeException("No se puede modificar materiales de una orden finalizada");
        }

        // Obtener el usuario responsable de la orden
        if (orden.getUsuarioId() == null) {
            throw new RuntimeException("La orden no tiene un usuario asignado");
        }
        String usuarioResponsable = "Usuario-" + orden.getUsuarioId(); // Podrías obtener el nombre real del usuario desde otro servicio

        // Validar que la nueva cantidad sea válida
        if (input.getNuevaCantidad() == null || input.getNuevaCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a cero");
        }

        // Guardar el valor anterior para el historial
        Integer cantidadAnterior = materialOrden.getCantidad();

        // Crear registro de historial con el usuario de la orden
        HistorialMaterialOrden historial = new HistorialMaterialOrden(
            materialOrden,
            "cantidad",
            cantidadAnterior.toString(),
            input.getNuevaCantidad().toString(),
            input.getJustificacion(),
            usuarioResponsable // Usuario obtenido de la orden
        );

        // Actualizar la cantidad
        materialOrden.setCantidad(input.getNuevaCantidad());
        
        // Guardar ambos registros
        MaterialOrden materialOrdenActualizado = materialOrdenRepository.save(materialOrden);
        historialMaterialOrdenRepository.save(historial);

        return materialOrdenActualizado;
    }

    @Transactional
    public MaterialOrden modificarTipoMaterial(ModificarTipoMaterialDTO input) {
        // Validar justificación obligatoria
        if (input.getJustificacion() == null || input.getJustificacion().trim().isEmpty()) {
            throw new RuntimeException("La justificación es obligatoria para modificar el tipo de material");
        }

        // Buscar el MaterialOrden
        MaterialOrden materialOrden = materialOrdenRepository.findById(input.getMaterialOrdenId())
            .orElseThrow(() -> new RuntimeException("Material de orden no encontrado"));

        // Obtener la orden y validar que no esté finalizada
        Orden orden = materialOrden.getOrden();
        if (orden.getEstado() != null && "Finalizada".equalsIgnoreCase(orden.getEstado().getNombre())) {
            throw new RuntimeException("No se puede modificar materiales de una orden finalizada");
        }

        // Buscar el nuevo material
        Material nuevoMaterial = materialRepository.findById(input.getNuevoMaterialId())
            .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        // Obtener el usuario responsable de la orden
        if (orden.getUsuarioId() == null) {
            throw new RuntimeException("La orden no tiene un usuario asignado");
        }
        String usuarioResponsable = "Usuario-" + orden.getUsuarioId();

        // Guardar el material anterior para el historial
        Material materialAnterior = materialOrden.getMaterial();

        // Crear registro de historial
        HistorialMaterialOrden historial = new HistorialMaterialOrden(
            materialOrden,
            "material",
            materialAnterior.getNombre(),
            nuevoMaterial.getNombre(),
            input.getJustificacion(),
            usuarioResponsable
        );

        // Actualizar el material
        materialOrden.setMaterial(nuevoMaterial);
        
        // Guardar ambos registros
        MaterialOrden materialOrdenActualizado = materialOrdenRepository.save(materialOrden);
        historialMaterialOrdenRepository.save(historial);

        return materialOrdenActualizado;
    }

    @Transactional
    public boolean eliminarMaterialOrden(Long materialOrdenId) {
        // Verificar si existe el material orden
        MaterialOrden materialOrden = materialOrdenRepository.findById(materialOrdenId)
            .orElseThrow(() -> new RuntimeException("Material de orden no encontrado"));

        // Verificar si la orden no está finalizada
        Orden orden = materialOrden.getOrden();
        if (orden.getEstado() != null && "Finalizada".equalsIgnoreCase(orden.getEstado().getNombre())) {
            throw new RuntimeException("No se puede eliminar materiales de una orden finalizada");
        }

        // Verificar si hay historiales asociados y eliminarlos primero
        List<HistorialMaterialOrden> historiales = historialMaterialOrdenRepository
            .findByMaterialOrdenIdOrderByFechaModificacionDesc(materialOrdenId);
        if (!historiales.isEmpty()) {
            historialMaterialOrdenRepository.deleteAll(historiales);
        }

        // Eliminar el material orden
        materialOrdenRepository.delete(materialOrden);
        return true;
    }

    public List<HistorialMaterialOrden> obtenerHistorialMaterial(Long materialOrdenId) {
        return historialMaterialOrdenRepository.findByMaterialOrdenIdOrderByFechaModificacionDesc(materialOrdenId);
    }
}