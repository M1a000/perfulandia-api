package cl.perfulandia.catalogo.assemblers;

import cl.perfulandia.catalogo.controller.DetalleOrdenControllerV2;
import cl.perfulandia.catalogo.model.DetalleOrden;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DetalleOrdenModelAssembler implements RepresentationModelAssembler<DetalleOrden, EntityModel<DetalleOrden>> {

    @Override
    @NonNull
    public EntityModel<DetalleOrden> toModel(@NonNull DetalleOrden detalleOrden) {
        return EntityModel.of(detalleOrden,
            linkTo(methodOn(DetalleOrdenControllerV2.class).getDetalleOrden(detalleOrden.getId())).withSelfRel(),
            linkTo(methodOn(DetalleOrdenControllerV2.class).getTodosDetallesOrden()).withRel("detalles-orden")
        );
    }
}
