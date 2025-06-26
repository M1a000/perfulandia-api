package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.UsuarioDTO;
import cl.perfulandia.catalogo.model.Usuario;
import cl.perfulandia.catalogo.repository.UsuarioRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepositoryJPA repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("admin@perfulandia.cl");
        usuario.setPassword("admin1234");
        usuario.setRol("ADMIN");
        usuario.setActivo(true);
    }

    @Test
    void obtenerUsuarioPorId_Existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.obtenerUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void crearUsuario_EmailUnico() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("nuevo@perfulandia.cl");
        
        when(repository.existsByEmail("nuevo@perfulandia.cl")).thenReturn(false);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.crearUsuario(dto);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(repository, times(1)).existsByEmail("nuevo@perfulandia.cl");
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void crearUsuario_EmailDuplicado() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("admin@perfulandia.cl");
        
        when(repository.existsByEmail("admin@perfulandia.cl")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            service.crearUsuario(dto);
        });
    }

    @Test
    void obtenerUsuarioPorId_NoExistente() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.obtenerUsuarioPorId(2L);
        });
    }
}
