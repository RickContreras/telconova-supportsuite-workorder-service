package com.telconova.support_backend.service;

import com.telconova.support_backend.entity.Orden;
import com.telconova.support_backend.entity.TipoEstado;
import com.telconova.support_backend.entity.TipoOrden;
import com.telconova.support_backend.entity.Cliente;
import com.telconova.support_backend.repository.OrdenRepository;
import com.telconova.support_backend.repository.TipoEstadoRepository;
import com.telconova.support_backend.repository.TipoOrdenRepository;
import com.telconova.support_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;


@Service
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private TipoEstadoRepository tipoEstadoRepository;

    @Autowired
    private TipoOrdenRepository tipoOrdenRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Orden> listarOrdenes() {
        return ordenRepository.findAll();
    }

    public Optional<Orden> obtenerOrdenPorId(Long id) {
        return ordenRepository.findById(id);
    }

    public Orden crearOrden(Orden orden) {
        return ordenRepository.save(orden);
    }

    public Orden cambiarEstadoOrdenOrThrow(Long id, String nuevoEstado) {
    Orden orden = ordenRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    if (orden.getEstado() != null && "Finalizada".equalsIgnoreCase(orden.getEstado().getNombre())) {
        throw new RuntimeException("No se puede modificar una orden finalizada");
    }
    TipoEstado tipoEstado = tipoEstadoRepository.findByNombre(nuevoEstado)
        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
    orden.setEstado(tipoEstado);
    return ordenRepository.save(orden);
    }

    public boolean eliminarOrden(Long id) {
        if (ordenRepository.existsById(id)) {
            ordenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Orden> actualizarEstado(Long id, String nuevoEstado) {
        return ordenRepository.findById(id).flatMap(orden -> {
            return tipoEstadoRepository.findByNombre(nuevoEstado).map(tipoEstado -> {
                orden.setEstado(tipoEstado);
                return ordenRepository.save(orden);
            });
        });
    }

    public String exportarOrdenesCsv() {
        List<Orden> ordenes = ordenRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("id,codigo,descripcion,estado,cliente,tipo\n");
        for (Orden o : ordenes) {
            sb.append(o.getId()).append(",")
              .append(o.getCodigo()).append(",")
              .append(o.getDescripcion()).append(",")
              .append(o.getEstado() != null ? o.getEstado().getNombre() : "").append(",")
              .append(o.getCliente() != null ? o.getCliente().getNombre() : "").append(",")
              .append(o.getTipo() != null ? o.getTipo().getNombre() : "").append("\n");
        }
        return sb.toString();
    }

    public List<Orden> filtrarOrdenes(String estado, Long clienteId, String fechaInicio, String fechaCierre) {
        List<Orden> ordenes = ordenRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (estado != null) {
            ordenes = ordenes.stream()
                .filter(o -> o.getEstado() != null && estado.equalsIgnoreCase(o.getEstado().getNombre()))
                .toList();
        }
        if (clienteId != null) {
            ordenes = ordenes.stream()
                .filter(o -> o.getCliente() != null && clienteId.equals(o.getCliente().getId()))
                .toList();
        }
        if (fechaInicio != null) {
            ordenes = ordenes.stream()
                .filter(o -> o.getFechaInicio() != null && o.getFechaInicio().format(formatter).startsWith(fechaInicio))
                .toList();
        }
        if (fechaCierre != null) {
            ordenes = ordenes.stream()
                .filter(o -> o.getFechaCierre() != null && o.getFechaCierre().format(formatter).startsWith(fechaCierre))
                .toList();
        }
        return ordenes;
    }

    public List<Orden> obtenerOrdenesPorUsuarioId(Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }
}
