package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.service.MarcaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo/marcas")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @GetMapping
    public List<MarcaDTO> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<MarcaDTO> create(@Validated @RequestBody MarcaDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> update(
        @PathVariable Long id,
        @Validated @RequestBody MarcaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}