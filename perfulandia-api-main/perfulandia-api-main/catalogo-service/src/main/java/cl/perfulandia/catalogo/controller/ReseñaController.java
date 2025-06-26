package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.service.ReseñaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/catalogo/reseñas")
public class ReseñaController {

    private final ReseñaService service;

    public ReseñaController(ReseñaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReseñaDTO> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReseñaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ReseñaDTO> create(@Validated @RequestBody ReseñaDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReseñaDTO> update(
        @PathVariable Long id,
        @Validated @RequestBody ReseñaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
