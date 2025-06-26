package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.assemblers.DetalleOrdenModelAssembler;
import cl.perfulandia.catalogo.model.DetalleOrden;
import cl.perfulandia.catalogo.service.DetalleOrdenService;
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

@WebMvcTest(DetalleOrdenControllerV2.class)
public class DetalleOrdenControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalleOrdenService detalleOrdenService;

    @MockBean
    private DetalleOrdenModelAssembler detalleOrdenModelAssembler;

    @Test
    void obtenerDetalleOrdenPorId() throws Exception {
       
        DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setId(1L);
        detalleOrden.setOrdenId(1L);
        detalleOrden.setProductoId(1L);
        detalleOrden.setCantidad(2);
        detalleOrden.setPrecioUnitario(99.99);

      
        when(detalleOrdenService.obtenerDetalleOrdenPorId(1L)).thenReturn(detalleOrden);
        when(detalleOrdenModelAssembler.toModel(detalleOrden))
            .thenReturn(EntityModel.of(detalleOrden));

        
        mockMvc.perform(get("/api/v2/detalleorden/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void crearDetalleOrden() throws Exception {
       
        DetalleOrdenDTO inputDTO = new DetalleOrdenDTO();
        inputDTO.setOrdenId(1L);
        inputDTO.setProductoId(1L);
        inputDTO.setCantidad(2);
        inputDTO.setPrecioUnitario(99.99);

        DetalleOrden detalleOrdenOutput = new DetalleOrden();
        detalleOrdenOutput.setId(1L);
        detalleOrdenOutput.setOrdenId(1L);
        detalleOrdenOutput.setProductoId(1L);
        detalleOrdenOutput.setCantidad(2);
        detalleOrdenOutput.setPrecioUnitario(99.99);

       
        when(detalleOrdenService.crearDetalleOrden(any(DetalleOrdenDTO.class))).thenReturn(detalleOrdenOutput);
        when(detalleOrdenModelAssembler.toModel(detalleOrdenOutput))
            .thenReturn(EntityModel.of(detalleOrdenOutput));

     
        mockMvc.perform(post("/api/v2/detalleorden")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ordenId\":1,\"productoId\":1,\"cantidad\":2,\"precioUnitario\":99.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cantidad").value(2));
    }
}
