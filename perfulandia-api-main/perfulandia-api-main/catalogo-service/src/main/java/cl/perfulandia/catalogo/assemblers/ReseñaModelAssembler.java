package cl.perfulandia.catalogo.assemblers;

import cl.perfulandia.catalogo.controller.ReseñaControllerV2;
import cl.perfulandia.catalogo.model.Reseña;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReseñaModelAssembler implements RepresentationModelAssembler<Reseña, EntityModel<Reseña>> {

    @Override
    @NonNull
    public EntityModel<Reseña> toModel(@NonNull Reseña reseña) {
        return EntityModel.of(reseña,
            linkTo(methodOn(ReseñaControllerV2.class).getReseña(reseña.getId())).withSelfRel(),
            linkTo(methodOn(ReseñaControllerV2.class).getTodasReseñas()).withRel("reseñas")
        );
    }
}
