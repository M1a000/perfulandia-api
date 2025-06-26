package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.assemblers.MarcaModelAssembler;
import cl.perfulandia.catalogo.model.Marca;
import cl.perfulandia.catalogo.service.MarcaService;
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

@WebMvcTest(MarcaControllerV2.class)
public class MarcaControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarcaService marcaService;

    @MockBean
    private MarcaModelAssembler marcaModelAssembler;

    @Test
    void obtenerMarcaPorId() throws Exception {
       
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("Chanel");
        marca.setDescripcion("Marca de lujo");
        marca.setActiva(true);

       
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marca);
        when(marcaModelAssembler.toModel(marca))
            .thenReturn(EntityModel.of(marca));

       
        mockMvc.perform(get("/api/v2/marcas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Chanel"));
    }

    @Test
    void crearMarca() throws Exception {
        
        MarcaDTO inputDTO = new MarcaDTO();
        inputDTO.setNombre("Gucci");
        inputDTO.setDescripcion("Marca italiana de lujo");
        inputDTO.setActiva(true);

      
        Marca marcaOutput = new Marca();
        marcaOutput.setId(1L);
        marcaOutput.setNombre("Gucci");
        marcaOutput.setDescripcion("Marca italiana de lujo");
        marcaOutput.setActiva(true);

       
        when(marcaService.crearMarca(any(MarcaDTO.class))).thenReturn(marcaOutput);
        when(marcaModelAssembler.toModel(marcaOutput))
            .thenReturn(EntityModel.of(marcaOutput));

        
        mockMvc.perform(post("/api/v2/marcas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Gucci\",\"descripcion\":\"Marca italiana de lujo\",\"activa\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Gucci"));
    }
}
