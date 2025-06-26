package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.Orden;
import cl.perfulandia.catalogo.service.OrdenService;
import cl.perfulandia.catalogo.assemblers.OrdenModelAssembler;
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

@Tag(name = "Orden V2", description = "Operaciones de órdenes con HATEOAS (versión 2)")
@RestController
@RequestMapping("/api/v2/ordenes")
public class OrdenControllerV2 {

    private final OrdenService ordenService;
    private final OrdenModelAssembler assembler;

    public OrdenControllerV2(OrdenService ordenService, 
                            OrdenModelAssembler assembler) {
        this.ordenService = ordenService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener orden por ID", description = "Devuelve una orden con enlaces HATEOAS")
    @GetMapping("/{id}")
    public EntityModel<Orden> getOrden(@PathVariable Long id) {
        Orden orden = ordenService.obtenerOrdenPorId(id);
        return assembler.toModel(orden);
    }

    @Operation(summary = "Obtener todas las órdenes", description = "Devuelve una colección de órdenes con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Orden>> getTodasOrdenes() {
        List<EntityModel<Orden>> ordenes = ordenService.obtenerTodasOrdenes().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(ordenes,
            linkTo(methodOn(OrdenControllerV2.class).getTodasOrdenes()).withSelfRel());
    }

    @Operation(summary = "Crear nueva orden", description = "Crea una nueva orden con validación de datos")
    @PostMapping
    public ResponseEntity<EntityModel<Orden>> crearOrden(
            @Validated(OrdenDTO.CreateValidation.class) @RequestBody OrdenDTO dto) {
        Orden orden = ordenService.crearOrden(dto);
        EntityModel<Orden> model = EntityModel.of(orden,
            linkTo(methodOn(OrdenControllerV2.class).getOrden(orden.getId())).withSelfRel(),
            linkTo(methodOn(OrdenControllerV2.class).getTodasOrdenes()).withRel("ordenes")
        );
        return ResponseEntity.created(
            linkTo(methodOn(OrdenControllerV2.class).getOrden(orden.getId())).toUri()
        ).body(model);
    }

    @Operation(summary = "Actualizar orden", description = "Actualiza una orden existente con validación de datos")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Orden>> actualizarOrden(
            @PathVariable Long id,
            @Validated(OrdenDTO.UpdateValidation.class) @RequestBody OrdenDTO dto) {
        Orden orden = ordenService.actualizarOrden(id, dto);
        EntityModel<Orden> model = EntityModel.of(orden,
            linkTo(methodOn(OrdenControllerV2.class).getOrden(id)).withSelfRel(),
            linkTo(methodOn(OrdenControllerV2.class).getTodasOrdenes()).withRel("ordenes")
        );
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar orden", description = "Elimina una orden existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrden(@PathVariable Long id) {
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }
}
