package cl.perfulandia.catalogo.controller;

import cl.perfulandia.catalogo.assemblers.UsuarioModelAssembler;
import cl.perfulandia.catalogo.model.Usuario;
import cl.perfulandia.catalogo.service.UsuarioService;
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

@WebMvcTest(UsuarioControllerV2.class)
public class UsuarioControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioModelAssembler usuarioModelAssembler;

    @Test
    void obtenerUsuarioPorId() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("admin@perfulandia.cl");
        usuario.setRol("ADMIN");
        usuario.setActivo(true);


        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuario);
        when(usuarioModelAssembler.toModel(usuario))
            .thenReturn(EntityModel.of(usuario));


        mockMvc.perform(get("/api/v2/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void crearUsuario() throws Exception {
  
        UsuarioDTO inputDTO = new UsuarioDTO();
        inputDTO.setNombre("Ana Gómez");
        inputDTO.setEmail("ana@perfulandia.cl");
        inputDTO.setRol("CLIENTE");
        
      
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana Gómez");
        usuario.setEmail("ana@perfulandia.cl");
        usuario.setRol("CLIENTE");
        usuario.setActivo(true);

      
        when(usuarioService.crearUsuario(any(UsuarioDTO.class))).thenReturn(usuario);
        when(usuarioModelAssembler.toModel(usuario))
            .thenReturn(EntityModel.of(usuario));

 
        mockMvc.perform(post("/api/v2/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Ana Gómez\",\"email\":\"ana@perfulandia.cl\",\"rol\":\"CLIENTE\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Ana Gómez"));
    }
}
