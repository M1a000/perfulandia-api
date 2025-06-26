package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.Categoria;
import cl.perfulandia.catalogo.service.CategoriaService;
import cl.perfulandia.catalogo.assemblers.CategoriaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Categoría V2", description = "Operaciones de categorías con HATEOAS (versión 2)")
@RestController
@RequestMapping("/api/v2/categorias")
public class CategoriaControllerV2 {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler assembler;

    public CategoriaControllerV2(CategoriaService categoriaService, 
                                CategoriaModelAssembler assembler) {
        this.categoriaService = categoriaService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener categoría por ID", description = "Devuelve una categoría con enlaces HATEOAS")
    @GetMapping("/{id}")
    public EntityModel<Categoria> getCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        return assembler.toModel(categoria);
    }

    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una colección de categorías con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Categoria>> getTodasCategorias() {
        List<EntityModel<Categoria>> categorias = categoriaService.obtenerTodasCategorias().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(categorias,
            linkTo(methodOn(CategoriaControllerV2.class).getTodasCategorias()).withSelfRel());
    }

    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría con validación de datos")
    @PostMapping
    public ResponseEntity<EntityModel<Categoria>> crearCategoria(
            @RequestBody CategoriaDTO dto) {
        Categoria categoria = categoriaService.crearCategoria(dto);
        EntityModel<Categoria> model = EntityModel.of(categoria,
            linkTo(methodOn(CategoriaControllerV2.class).getCategoria(categoria.getId())).withSelfRel(),
            linkTo(methodOn(CategoriaControllerV2.class).getTodasCategorias()).withRel("categorias")
        );
        return ResponseEntity.created(
            linkTo(methodOn(CategoriaControllerV2.class).getCategoria(categoria.getId())).toUri()
        ).body(model);
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente con validación de datos")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaDTO dto) {
        Categoria categoria = categoriaService.actualizarCategoria(id, dto);
        EntityModel<Categoria> model = EntityModel.of(categoria,
            linkTo(methodOn(CategoriaControllerV2.class).getCategoria(id)).withSelfRel(),
            linkTo(methodOn(CategoriaControllerV2.class).getTodasCategorias()).withRel("categorias")
        );
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
