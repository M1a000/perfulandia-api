package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.CategoriaDTO;
import cl.perfulandia.catalogo.model.Categoria;
import cl.perfulandia.catalogo.repository.CategoriaRepositoryJPA;
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
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepositoryJPA repository;

    @InjectMocks
    private CategoriaService service;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Perfumes");
        categoria.setDescripcion("Descripción de perfumes");
        categoria.setActiva(true);
    }

    @Test
    void obtenerCategoriaPorId_Existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria resultado = service.obtenerCategoriaPorId(1L);

        assertNotNull(resultado);
        assertEquals("Perfumes", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void crearCategoria_NombreUnico() {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Nuevas Fragancias");
        
        when(repository.existsByNombre("Nuevas Fragancias")).thenReturn(false);
        when(repository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria resultado = service.crearCategoria(dto);

        assertNotNull(resultado);
        assertEquals("Perfumes", resultado.getNombre());
        verify(repository, times(1)).existsByNombre("Nuevas Fragancias");
        verify(repository, times(1)).save(any(Categoria.class));
    }

    @Test
    void crearCategoria_NombreDuplicado() {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Perfumes");
        
        when(repository.existsByNombre("Perfumes")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            service.crearCategoria(dto);
        });
    }

    @Test
    void obtenerCategoriaPorId_NoExistente() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.obtenerCategoriaPorId(2L);
        });
    }
}
