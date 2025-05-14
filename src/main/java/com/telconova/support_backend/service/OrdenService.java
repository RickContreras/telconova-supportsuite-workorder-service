package com.telconova.support_backend.service;

import com.telconova.support_backend.entity.Orden;
import com.telconova.support_backend.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;

    public List<Orden> listarOrdenes() {
        return ordenRepository.findAll();
    }

    public Optional<Orden> obtenerOrdenPorId(Long id) {
        return ordenRepository.findById(id);
    }

    public Orden crearOrden(Orden orden) {
        return ordenRepository.save(orden);
    }

    public Optional<Orden> actualizarOrden(Long id, Orden ordenActualizada) {
        return ordenRepository.findById(id).map(orden -> {
            orden.setCodigo(ordenActualizada.getCodigo());
            orden.setDescripcion(ordenActualizada.getDescripcion());
            orden.setTipo(ordenActualizada.getTipo());
            orden.setCliente(ordenActualizada.getCliente());
            orden.setFechaCreacion(ordenActualizada.getFechaCreacion());
            orden.setFechaModificacion(ordenActualizada.getFechaModificacion());
            orden.setFechaCierre(ordenActualizada.getFechaCierre());
            orden.setFechaInicio(ordenActualizada.getFechaInicio());
            orden.setEstado(ordenActualizada.getEstado());
            return ordenRepository.save(orden);
        });
    }

    public boolean eliminarOrden(Long id) {
        if (ordenRepository.existsById(id)) {
            ordenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Orden> actualizarEstado(Long id, String nuevoEstado) {
        return ordenRepository.findById(id).map(orden -> {
            // Aquí deberías buscar el TipoEstado correspondiente y asignarlo
            // orden.setEstado(tipoEstado);
            return ordenRepository.save(orden);
        });
    }

    public List<Orden> filtrarOrdenes(String estado, Long clienteId, Long tipoId) {
        if (estado != null) {
            return ordenRepository.findByEstado_Nombre(estado);
        } else if (clienteId != null) {
            return ordenRepository.findByCliente_Id(clienteId);
        } else if (tipoId != null) {
            return ordenRepository.findByTipo_Id(tipoId);
        } else {
            return ordenRepository.findAll();
        }
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


}
