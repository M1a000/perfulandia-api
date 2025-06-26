package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.assemblers.ProductoModelAssembler;
import cl.perfulandia.catalogo.model.Producto;
import cl.perfulandia.catalogo.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoControllerV2.class)
public class ProductoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private ProductoModelAssembler productoModelAssembler;

    @Test
    void obtenerProductoPorId() throws Exception {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Chanel N°5");
        producto.setPrecio(120.99);
        producto.setDescripcion("Fragancia floral aldehídica para mujer");
        producto.setActivo(true);

        when(productoService.obtenerProductoPorId(1L)).thenReturn(producto);
        when(productoModelAssembler.toModel(producto))
            .thenReturn(EntityModel.of(producto));


        mockMvc.perform(get("/api/v2/productos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Chanel N°5"));
    }

    @Test
    void crearProducto() throws Exception {

        ProductoDTO inputDTO = new ProductoDTO();
        inputDTO.setNombre("Dior J'adore");
        inputDTO.setPrecio(110.99);
        inputDTO.setDescripcion("Fragancia floral elegante");
        inputDTO.setActivo(true);

        Producto productoOutput = new Producto();
        productoOutput.setId(1L);
        productoOutput.setNombre("Dior J'adore");
        productoOutput.setPrecio(110.99);
        productoOutput.setDescripcion("Fragancia floral elegante");
        productoOutput.setActivo(true);

        when(productoService.crearProducto(any(ProductoDTO.class))).thenReturn(productoOutput);
        when(productoModelAssembler.toModel(productoOutput))
            .thenReturn(EntityModel.of(productoOutput));

        mockMvc.perform(post("/api/v2/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Dior J'adore\",\"precio\":110.99,\"descripcion\":\"Fragancia floral elegante\",\"activo\":true}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Dior J'adore"));
    }
}
