package cl.perfulandia.catalogo.assemblers;

import cl.perfulandia.catalogo.controller.UsuarioControllerV2;
import cl.perfulandia.catalogo.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    @NonNull
    public EntityModel<Usuario> toModel(@NonNull Usuario usuario) {
        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).getTodosUsuarios()).withRel("usuarios")
        );
    }
}
