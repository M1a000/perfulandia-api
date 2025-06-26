package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.Marca;
import cl.perfulandia.catalogo.service.MarcaService;
import cl.perfulandia.catalogo.assemblers.MarcaModelAssembler;
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

@Tag(name = "Marca V2", description = "Operaciones de marcas con HATEOAS (versión 2)")
@RestController
@RequestMapping("/api/v2/marcas")
public class MarcaControllerV2 {

    private final MarcaService marcaService;
    private final MarcaModelAssembler assembler;

    public MarcaControllerV2(MarcaService marcaService, 
                            MarcaModelAssembler assembler) {
        this.marcaService = marcaService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener marca por ID", description = "Devuelve una marca con enlaces HATEOAS")
    @GetMapping("/{id}")
    public EntityModel<Marca> getMarca(@PathVariable Long id) {
        Marca marca = marcaService.obtenerMarcaPorId(id);
        return assembler.toModel(marca);
    }

    @Operation(summary = "Obtener todas las marcas", description = "Devuelve una colección de marcas con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Marca>> getTodasMarcas() {
        List<EntityModel<Marca>> marcas = marcaService.obtenerTodasMarcas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(marcas,
            linkTo(methodOn(MarcaControllerV2.class).getTodasMarcas()).withSelfRel());
    }

    @Operation(summary = "Crear nueva marca", description = "Crea una nueva marca con validación de datos")
    @PostMapping
    public ResponseEntity<EntityModel<Marca>> crearMarca(
            @Validated(MarcaDTO.CreateValidation.class) @RequestBody MarcaDTO dto) {
        Marca marca = marcaService.crearMarca(dto);
        EntityModel<Marca> model = EntityModel.of(marca,
            linkTo(methodOn(MarcaControllerV2.class).getMarca(marca.getId())).withSelfRel(),
            linkTo(methodOn(MarcaControllerV2.class).getTodasMarcas()).withRel("marcas")
        );
        return ResponseEntity.created(
            linkTo(methodOn(MarcaControllerV2.class).getMarca(marca.getId())).toUri()
        ).body(model);
    }

    @Operation(summary = "Actualizar marca", description = "Actualiza una marca existente con validación de datos")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Marca>> actualizarMarca(
            @PathVariable Long id,
            @Validated(MarcaDTO.UpdateValidation.class) @RequestBody MarcaDTO dto) {
        Marca marca = marcaService.actualizarMarca(id, dto);
        EntityModel<Marca> model = EntityModel.of(marca,
            linkTo(methodOn(MarcaControllerV2.class).getMarca(id)).withSelfRel(),
            linkTo(methodOn(MarcaControllerV2.class).getTodasMarcas()).withRel("marcas")
        );
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar marca", description = "Elimina una marca existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarMarca(id);
        return ResponseEntity.noContent().build();
    }
}
