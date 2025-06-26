package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.Producto;
import cl.perfulandia.catalogo.service.ProductoService;
import cl.perfulandia.catalogo.assemblers.ProductoModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Producto V2", description = "Operaciones de productos con HATEOAS (versión 2)")
@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    private final ProductoService productoService;
    private final ProductoModelAssembler assembler;

    public ProductoControllerV2(ProductoService productoService, 
                               ProductoModelAssembler assembler) {
        this.productoService = productoService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto con enlaces HATEOAS")
    @GetMapping("/{id}")
    public EntityModel<Producto> getProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        return assembler.toModel(producto);
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una colección de productos con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Producto>> getTodosProductos() {
        List<EntityModel<Producto>> productos = productoService.obtenerTodosProductos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(productos,
            linkTo(methodOn(ProductoControllerV2.class).getTodosProductos()).withSelfRel());
    }

    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto con validación de datos")
    @PostMapping
    public ResponseEntity<EntityModel<Producto>> crearProducto(
            @Validated(ProductoDTO.CreateValidation.class) @RequestBody ProductoDTO dto) {
        Producto producto = productoService.crearProducto(dto);
        EntityModel<Producto> model = EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class).getProducto(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getTodosProductos()).withRel("productos")
        );
        return ResponseEntity.created(
            linkTo(methodOn(ProductoControllerV2.class).getProducto(producto.getId())).toUri()
        ).body(model);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente con validación de datos")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> actualizarProducto(
            @PathVariable Long id,
            @Validated(ProductoDTO.UpdateValidation.class) @RequestBody ProductoDTO dto) {
        Producto producto = productoService.actualizarProducto(id, dto);
        EntityModel<Producto> model = EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class).getProducto(id)).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getTodosProductos()).withRel("productos")
        );
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
