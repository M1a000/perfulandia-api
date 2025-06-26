package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.DetalleOrdenDTO;
import cl.perfulandia.catalogo.model.DetalleOrden;
import cl.perfulandia.catalogo.repository.DetalleOrdenRepositoryJPA;
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
public class DetalleOrdenServiceTest {

    @Mock
    private DetalleOrdenRepositoryJPA repository;

    @InjectMocks
    private DetalleOrdenService service;

    private DetalleOrden detalleOrden;

    @BeforeEach
    void setUp() {
        detalleOrden = new DetalleOrden();
        detalleOrden.setId(1L);
        detalleOrden.setOrdenId(1L);
        detalleOrden.setProductoId(1L);
        detalleOrden.setCantidad(2);
        detalleOrden.setPrecioUnitario(99.99);
    }

    @Test
    void obtenerDetalleOrdenPorId_Existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(detalleOrden));

        DetalleOrden resultado = service.obtenerDetalleOrdenPorId(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.getCantidad());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void crearDetalleOrden_Valido() {
        DetalleOrdenDTO dto = new DetalleOrdenDTO();
        dto.setOrdenId(1L);
        dto.setProductoId(1L);
        dto.setCantidad(2);
        dto.setPrecioUnitario(99.99);

        when(repository.save(any(DetalleOrden.class))).thenReturn(detalleOrden);

        DetalleOrden resultado = service.crearDetalleOrden(dto);

        assertNotNull(resultado);
        assertEquals(2, resultado.getCantidad());
        verify(repository, times(1)).save(any(DetalleOrden.class));
    }

    @Test
    void obtenerDetalleOrdenPorId_NoExistente() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.obtenerDetalleOrdenPorId(2L);
        });
    }
}
