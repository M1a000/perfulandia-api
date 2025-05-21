package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.service.DetalleOrdenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo/detalles-orden")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService service;

    @GetMapping
    public List<DetalleOrdenDTO> list(@RequestParam(required = false) Long ordenId) {
        if (ordenId != null) return service.filterByOrden(ordenId);
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrdenDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<DetalleOrdenDTO> create(@Validated @RequestBody DetalleOrdenDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleOrdenDTO> update(@PathVariable Long id, @Validated @RequestBody DetalleOrdenDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}