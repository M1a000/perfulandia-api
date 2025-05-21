package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<CategoriaDTO> list(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) Boolean activa
    ) {
        if (nombre != null) return service.searchByNombre(nombre);
        if (activa != null) return service.filterByActiva(activa);
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> create(@Validated @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @Validated @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}