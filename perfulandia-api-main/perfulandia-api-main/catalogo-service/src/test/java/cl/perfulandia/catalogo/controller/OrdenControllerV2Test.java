package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.assemblers.OrdenModelAssembler;
import cl.perfulandia.catalogo.model.Orden;
import cl.perfulandia.catalogo.service.OrdenService;
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

@WebMvcTest(OrdenControllerV2.class)
public class OrdenControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;

    @MockBean
    private OrdenModelAssembler ordenModelAssembler;

    @Test
    void obtenerOrdenPorId() throws Exception {
       
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setClienteId(1L);
        orden.setEstado("COMPLETADA");

       
        when(ordenService.obtenerOrdenPorId(1L)).thenReturn(orden);
        when(ordenModelAssembler.toModel(orden))
            .thenReturn(EntityModel.of(orden));

        
        mockMvc.perform(get("/api/v2/ordenes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("COMPLETADA"));
    }

    @Test
    void crearOrden() throws Exception {
       
        OrdenDTO inputDTO = new OrdenDTO();
        inputDTO.setClienteId(1L);
        inputDTO.setEstado("PENDIENTE");

       
        Orden ordenOutput = new Orden();
        ordenOutput.setId(1L);
        ordenOutput.setClienteId(1L);
        ordenOutput.setEstado("PENDIENTE");

        
        when(ordenService.crearOrden(any(OrdenDTO.class))).thenReturn(ordenOutput);
        when(ordenModelAssembler.toModel(ordenOutput))
            .thenReturn(EntityModel.of(ordenOutput));

      
        mockMvc.perform(post("/api/v2/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":1,\"estado\":\"PENDIENTE\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }
}
