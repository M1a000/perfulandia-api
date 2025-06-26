package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.OrdenDTO;
import cl.perfulandia.catalogo.model.Orden;
import cl.perfulandia.catalogo.repository.OrdenRepositoryJPA;
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
public class OrdenServiceTest {

    @Mock
    private OrdenRepositoryJPA repository;

    @InjectMocks
    private OrdenService service;

    private Orden orden;

    @BeforeEach
    void setUp() {
        orden = new Orden();
        orden.setId(1L);
        orden.setClienteId(1L);
        orden.setEstado("COMPLETADA");
    }

    @Test
    void obtenerOrdenPorId_Existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(orden));

        Orden resultado = service.obtenerOrdenPorId(1L);

        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstado());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void crearOrden_Valido() {
        OrdenDTO dto = new OrdenDTO();
        dto.setClienteId(1L);
        dto.setEstado("PENDIENTE");

        when(repository.save(any(Orden.class))).thenReturn(orden);

        Orden resultado = service.crearOrden(dto);

        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstado());
        verify(repository, times(1)).save(any(Orden.class));
    }

    @Test
    void obtenerOrdenPorId_NoExistente() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.obtenerOrdenPorId(2L);
        });
    }
}
