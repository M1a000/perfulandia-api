package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.assemblers.CategoriaModelAssembler;
import cl.perfulandia.catalogo.model.Categoria;
import cl.perfulandia.catalogo.service.CategoriaService;
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

@WebMvcTest(CategoriaControllerV2.class)
public class CategoriaControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaModelAssembler categoriaModelAssembler;

    @Test
    void obtenerCategoriaPorId() throws Exception {
       
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Perfumes");
        categoria.setDescripcion("Descripción de perfumes");
        categoria.setActiva(true);

      
        when(categoriaService.obtenerCategoriaPorId(1L)).thenReturn(categoria);
        when(categoriaModelAssembler.toModel(categoria))
            .thenReturn(EntityModel.of(categoria));

        
        mockMvc.perform(get("/api/v2/categorias/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfumes"));
    }

    @Test
    void crearCategoria() throws Exception {
        
        CategoriaDTO inputDTO = new CategoriaDTO();
        inputDTO.setNombre("Fragancias");
        inputDTO.setDescripcion("Descripción de fragancias");
        inputDTO.setActiva(true);

      
        Categoria categoriaOutput = new Categoria();
        categoriaOutput.setId(1L);
        categoriaOutput.setNombre("Fragancias");
        categoriaOutput.setDescripcion("Descripción de fragancias");
        categoriaOutput.setActiva(true);

        
        when(categoriaService.crearCategoria(any(CategoriaDTO.class))).thenReturn(categoriaOutput);
        when(categoriaModelAssembler.toModel(categoriaOutput))
            .thenReturn(EntityModel.of(categoriaOutput));

        
        mockMvc.perform(post("/api/v2/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Fragancias\",\"descripcion\":\"Descripción de fragancias\",\"activa\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Fragancias"));
    }
}
