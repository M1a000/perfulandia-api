package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.assemblers.ReseñaModelAssembler;
import cl.perfulandia.catalogo.model.Reseña;
import cl.perfulandia.catalogo.service.ReseñaService;
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

@WebMvcTest(ReseñaControllerV2.class)
public class ReseñaControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReseñaService reseñaService;

    @MockBean
    private ReseñaModelAssembler reseñaModelAssembler;

    @Test
    void obtenerReseñaPorId() throws Exception {

        Reseña reseña = new Reseña();
        reseña.setId(1L);
        reseña.setProductoId(1L);
        reseña.setClienteId(1L);
        reseña.setPuntuacion(5);
        reseña.setComentario("Excelente");


        when(reseñaService.obtenerReseñaPorId(1L)).thenReturn(reseña);
        when(reseñaModelAssembler.toModel(reseña))
            .thenReturn(EntityModel.of(reseña));


        mockMvc.perform(get("/api/v2/reseñas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puntuacion").value(5));
    }

    @Test
    void crearReseña() throws Exception {

        ReseñaDTO inputDTO = new ReseñaDTO();
        inputDTO.setProductoId(1L);
        inputDTO.setClienteId(1L);
        inputDTO.setPuntuacion(5);
        inputDTO.setComentario("Excelente");
        

        Reseña reseñaOutput = new Reseña();
        reseñaOutput.setId(1L);
        reseñaOutput.setProductoId(1L);
        reseñaOutput.setClienteId(1L);
        reseñaOutput.setPuntuacion(5);
        reseñaOutput.setComentario("Excelente");


        when(reseñaService.crearReseña(any(ReseñaDTO.class))).thenReturn(reseñaOutput);
        when(reseñaModelAssembler.toModel(reseñaOutput))
            .thenReturn(EntityModel.of(reseñaOutput));

        mockMvc.perform(post("/api/v2/reseñas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productoId\":1,\"clienteId\":1,\"puntuacion\":5,\"comentario\":\"Excelente\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.puntuacion").value(5));
    }
}
