package cl.perfulandia.catalogo.assemblers;

import cl.perfulandia.catalogo.controller.ProductoControllerV2;
import cl.perfulandia.catalogo.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    @NonNull
    public EntityModel<Producto> toModel(@NonNull Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class).getProducto(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getTodosProductos()).withRel("productos")
        );
    }
}
