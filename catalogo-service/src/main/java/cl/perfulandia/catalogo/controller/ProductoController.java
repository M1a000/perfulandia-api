package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/catalogo/productos")
public class ProductoController {
    @Autowired
    private ProductoService service;

    @GetMapping
    public List<ProductoDTO> list(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String categoria) {
        if (nombre != null) return service.searchProductsByName(nombre);
        if (categoria != null) return service.filterProductsByCategory(categoria);
        return service.getAllProductDtos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> create(@Validated @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(
        @PathVariable Long id,
        @Validated @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(service.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

