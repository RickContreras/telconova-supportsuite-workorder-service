package com.telconova.support_backend.controller;
import com.telconova.support_backend.entity.Orden;
import com.telconova.support_backend.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @QueryMapping
    public List<Orden> listarOrdenes() {
        return ordenService.listarOrdenes();
    }

    @QueryMapping
    public Orden obtenerOrdenPorId(@Argument Long id) {
        return ordenService.obtenerOrdenPorId(id).orElse(null);
    }

    @QueryMapping
    public List<Orden> filtrarOrdenes(@Argument String estado, @Argument Long clienteId, @Argument String fechaInicio, @Argument String fechaCierre) {
        return ordenService.filtrarOrdenes(estado, clienteId, fechaInicio, fechaCierre);
    }

    @MutationMapping
    public Orden cambiarEstadoOrden(@Argument Long id, @Argument String estado) {
    return ordenService.cambiarEstadoOrdenOrThrow(id, estado);
    }
    /*@PostMapping
    public Orden crearOrden(@RequestBody Orden orden) {
        return ordenService.crearOrden(orden);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orden> actualizarOrden(@PathVariable Long id, @RequestBody Orden orden) {
        return ordenService.actualizarOrden(id, orden)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        if (ordenService.eliminarOrden(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Orden> actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        return ordenService.actualizarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtro")
    public List<Orden> filtrarOrdenes(@RequestParam(required = false) String estado,
                                      @RequestParam(required = false) Long clienteId,
                                      @RequestParam(required = false) Long tipoId) {
        return ordenService.filtrarOrdenes(estado, clienteId, tipoId);
    }

    @GetMapping("/export/{formato}")
    public ResponseEntity<String> exportarOrdenes(@PathVariable String formato) {
        // Por simplicidad, solo exporta CSV como ejemplo
        String csv = ordenService.exportarOrdenesCsv();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=ordenes.csv")
                .body(csv);
    }
    */
    @QueryMapping
    public List<Orden> obtenerOrdenesPorUsuarioId(@Argument Long usuario_id) {
        return ordenService.obtenerOrdenesPorUsuarioId(usuario_id);
    }

    // Métodos para filtro y exportación se pueden agregar aquí
}