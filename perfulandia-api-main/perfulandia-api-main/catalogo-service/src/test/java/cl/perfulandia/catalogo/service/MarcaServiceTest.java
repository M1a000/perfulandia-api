package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.MarcaDTO;
import cl.perfulandia.catalogo.model.Marca;
import cl.perfulandia.catalogo.repository.MarcaRepositoryJPA;
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
public class MarcaServiceTest {

    @Mock
    private MarcaRepositoryJPA repository;

    @InjectMocks
    private MarcaService service;

    private Marca marca;

    @BeforeEach
    void setUp() {
        marca = new Marca();
        marca.setId(1L);
        marca.setNombre("Chanel");
        marca.setDescripcion("Marca de lujo");
        marca.setActiva(true);
    }

    @Test
    void obtenerMarcaPorId_Existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(marca));

        Marca resultado = service.obtenerMarcaPorId(1L);

        assertNotNull(resultado);
        assertEquals("Chanel", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void crearMarca_NombreUnico() {
        MarcaDTO dto = new MarcaDTO();
        dto.setNombre("Gucci");
        
        when(repository.existsByNombre("Gucci")).thenReturn(false);
        when(repository.save(any(Marca.class))).thenReturn(marca);

        Marca resultado = service.crearMarca(dto);

        assertNotNull(resultado);
        assertEquals("Chanel", resultado.getNombre());
        verify(repository, times(1)).existsByNombre("Gucci");
        verify(repository, times(1)).save(any(Marca.class));
    }

    @Test
    void crearMarca_NombreDuplicado() {
        MarcaDTO dto = new MarcaDTO();
        dto.setNombre("Chanel");
        
        when(repository.existsByNombre("Chanel")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            service.crearMarca(dto);
        });
    }
}
