package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.Categoria;
import cl.perfulandia.catalogo.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Categoría V2", description = "Operaciones de categorías (versión 2)")
@RestController
@RequestMapping("/api/v2/categorias")
public class CategoriaControllerV2 {

    private final CategoriaService categoriaService;

    public CategoriaControllerV2(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Obtener categoría por ID (V2)", description = "Devuelve una categoría dado su ID (versión 2)")
    @GetMapping("/{id}")
    public EntityModel<Categoria> getCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaService.ObtenerCategoriaPorId(id);
        return EntityModel.of(categoria,
            linkTo(methodOn(CategoriaControllerV2.class).getCategoria(id)).withSelfRel(),
            linkTo(methodOn(CategoriaControllerV2.class).getTodasCategorias()).withRel("categorias"));
    }

    @Operation(summary = "Obtener todas las categorías (V2)", description = "Devuelve todas las categorías (versión 2)")
    @GetMapping
    public CollectionModel<EntityModel<Categoria>> getTodasCategorias() {
        List<EntityModel<Categoria>> categorias = categoriaService.ObtenerTodasCategorias().stream()
            .map(categoria -> EntityModel.of(categoria,
                linkTo(methodOn(CategoriaControllerV2.class).getCategoria(categoria.getId())).withSelfRel()))
            .collect(Collectors.toList());
        return CollectionModel.of(categorias,
            linkTo(methodOn(CategoriaControllerV2.class).getTodasCategorias()).withSelfRel());
    }
}

