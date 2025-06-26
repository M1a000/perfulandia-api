package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.model.DetalleOrden;
import cl.perfulandia.catalogo.service.DetalleOrdenService;
import cl.perfulandia.catalogo.assemblers.DetalleOrdenModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "DetalleOrden V2", description = "Operaciones de detalles de orden con HATEOAS (versión 2)")
@RestController
@RequestMapping("/api/v2/detalleorden")
public class DetalleOrdenControllerV2 {

    private final DetalleOrdenService detalleOrdenService;
    private final DetalleOrdenModelAssembler assembler;

    public DetalleOrdenControllerV2(DetalleOrdenService detalleOrdenService, 
                                   DetalleOrdenModelAssembler assembler) {
        this.detalleOrdenService = detalleOrdenService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener detalle de orden por ID", description = "Devuelve un detalle de orden con enlaces HATEOAS")
    @GetMapping("/{id}")
    public EntityModel<DetalleOrden> getDetalleOrden(@PathVariable Long id) {
        DetalleOrden detalleOrden = detalleOrdenService.obtenerDetalleOrdenPorId(id);
        return assembler.toModel(detalleOrden);
    }

    @Operation(summary = "Obtener todos los detalles de orden", description = "Devuelve una colección de detalles de orden con enlaces HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<DetalleOrden>> getTodosDetallesOrden() {
        List<EntityModel<DetalleOrden>> detalles = detalleOrdenService.obtenerTodosDetallesOrden().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(detalles,
            linkTo(methodOn(DetalleOrdenControllerV2.class).getTodosDetallesOrden()).withSelfRel());
    }

    @Operation(summary = "Crear nuevo detalle de orden", description = "Crea un nuevo detalle de orden")
    @PostMapping
    public ResponseEntity<EntityModel<DetalleOrden>> crearDetalleOrden(@RequestBody DetalleOrdenDTO dto) {
        DetalleOrden detalleOrden = detalleOrdenService.crearDetalleOrden(dto);
        EntityModel<DetalleOrden> model = EntityModel.of(detalleOrden,
            linkTo(methodOn(DetalleOrdenControllerV2.class).getDetalleOrden(detalleOrden.getId())).withSelfRel(),
            linkTo(methodOn(DetalleOrdenControllerV2.class).getTodosDetallesOrden()).withRel("detalles-orden")
        );
        return ResponseEntity.created(
            linkTo(methodOn(DetalleOrdenControllerV2.class).getDetalleOrden(detalleOrden.getId())).toUri()
        ).body(model);
    }

    @Operation(summary = "Actualizar detalle de orden", description = "Actualiza un detalle de orden existente")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleOrden>> actualizarDetalleOrden(
            @PathVariable Long id,
            @RequestBody DetalleOrdenDTO dto) {
        DetalleOrden detalleOrden = detalleOrdenService.actualizarDetalleOrden(id, dto);
        EntityModel<DetalleOrden> model = EntityModel.of(detalleOrden,
            linkTo(methodOn(DetalleOrdenControllerV2.class).getDetalleOrden(id)).withSelfRel(),
            linkTo(methodOn(DetalleOrdenControllerV2.class).getTodosDetallesOrden()).withRel("detalles-orden")
        );
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Eliminar detalle de orden", description = "Elimina un detalle de orden existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDetalleOrden(@PathVariable Long id) {
        detalleOrdenService.eliminarDetalleOrden(id);
        return ResponseEntity.noContent().build();
    }
}
