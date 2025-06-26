package cl.perfulandia.catalogo.assemblers;

import cl.perfulandia.catalogo.controller.OrdenControllerV2;
import cl.perfulandia.catalogo.model.Orden;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrdenModelAssembler implements RepresentationModelAssembler<Orden, EntityModel<Orden>> {

    @Override
    @NonNull
    public EntityModel<Orden> toModel(@NonNull Orden orden) {
        return EntityModel.of(orden,
            linkTo(methodOn(OrdenControllerV2.class).getOrden(orden.getId())).withSelfRel(),
            linkTo(methodOn(OrdenControllerV2.class).getTodasOrdenes()).withRel("ordenes")
        );
    }
}
