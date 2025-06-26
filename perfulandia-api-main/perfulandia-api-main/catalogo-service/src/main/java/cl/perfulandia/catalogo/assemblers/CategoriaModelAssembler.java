package cl.perfulandia.catalogo.assemblers;

import cl.perfulandia.catalogo.model.Categoria;
import cl.perfulandia.catalogo.controller.CategoriaControllerV2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {

    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(categoria,
            linkTo(methodOn(CategoriaControllerV2.class).getCategoria(categoria.getId())).withSelfRel(),
            linkTo(methodOn(CategoriaControllerV2.class).getTodasCategorias()).withRel("categorias")
        );
    }
}
