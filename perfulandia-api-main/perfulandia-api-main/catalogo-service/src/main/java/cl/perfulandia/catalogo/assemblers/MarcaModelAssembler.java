package cl.perfulandia.catalogo.assemblers;

import cl.perfulandia.catalogo.controller.MarcaControllerV2;
import cl.perfulandia.catalogo.model.Marca;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MarcaModelAssembler implements RepresentationModelAssembler<Marca, EntityModel<Marca>> {

    @Override
    @NonNull
    public EntityModel<Marca> toModel(@NonNull Marca marca) {
        return EntityModel.of(marca,
            linkTo(methodOn(MarcaControllerV2.class).getMarca(marca.getId())).withSelfRel(),
            linkTo(methodOn(MarcaControllerV2.class).getTodasMarcas()).withRel("marcas")
        );
    }
}
