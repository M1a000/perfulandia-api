package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.Reseña;
import cl.perfulandia.catalogo.service.ReseñaService;
import cl.perfulandia.catalogo.assemblers.ReseñaModelAssembler;
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

@Tag(name = "Reseña V2", description = "Operaciones de reseñas con HATEOAS (versión 2)")
@RestController
@RequestMapping("/api/v2/resenas")
public class ReseñaControllerV2 {

    private final ReseñaService reseñaService;
    private final ReseñaModelAssembler assembler;

    public ReseñaControllerV2(ReseñaService reseñaService, 
                             ReseñaModelAssembler assembler) {
        this.reseñaService = reseñaService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener reseña por ID", description = "Devuelve una reseña con enlaces HATEOAS")
    @GetMapping("/{id}")
    public EntityModel<Reseña> getReseña(@PathVariable Long id) {
        Reseña reseña = reseñaService.obtenerReseñaPorId(id);
        return assembler.toModel(reseña);
    }

    @Operation(summary = "Obtener todas las reseñas", description = "Devuelve una colección de reseñas con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Reseña>> getTodasReseñas() {
        List<EntityModel<Reseña>> reseñas = reseñaService.obtenerTodasReseñas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(reseñas,
            linkTo(methodOn(ReseñaControllerV2.class).getTodasReseñas()).withSelfRel());
    }

    @Operation(summary = "Crear nueva reseña", description = "Crea una nueva reseña con validación de datos")
    @PostMapping
    public ResponseEntity<EntityModel<Reseña>> crearReseña(
            @Validated(ReseñaDTO.CreateValidation.class) @RequestBody ReseñaDTO dto) {
        Reseña reseña = reseñaService.crearReseña(dto);
        EntityModel<Reseña> model = EntityModel.of(reseña,
            linkTo(methodOn(ReseñaControllerV2.class).getReseña(reseña.getId())).withSelfRel(),
            linkTo(methodOn(ReseñaControllerV2.class).getTodasReseñas()).withRel("reseñas")
        );
        return ResponseEntity.created(
            linkTo(methodOn(ReseñaControllerV2.class).getReseña(reseña.getId())).toUri()
        ).body(model);
    }

    @Operation(summary = "Actualizar reseña", description = "Actualiza una reseña existente con validación de datos")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reseña>> actualizarReseña(
            @PathVariable Long id,
            @Validated(ReseñaDTO.UpdateValidation.class) @RequestBody ReseñaDTO dto) {
        Reseña reseña = reseñaService.actualizarReseña(id, dto);
        EntityModel<Reseña> model = EntityModel.of(reseña,
            linkTo(methodOn(ReseñaControllerV2.class).getReseña(id)).withSelfRel(),
            linkTo(methodOn(ReseñaControllerV2.class).getTodasReseñas()).withRel("reseñas")
        );
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReseña(@PathVariable Long id) {
        reseñaService.eliminarReseña(id);
        return ResponseEntity.noContent().build();
    }
}
