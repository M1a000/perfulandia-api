package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.ReseñaDTO;
import cl.perfulandia.catalogo.model.Reseña;
import cl.perfulandia.catalogo.repository.ReseñaRepositoryJPA;
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
public class ReseñaServiceTest {

    @Mock
    private ReseñaRepositoryJPA repository;

    @InjectMocks
    private ReseñaService service;

    private Reseña reseña;

    @BeforeEach
    void setUp() {
        reseña = new Reseña();
        reseña.setId(1L);
        reseña.setProductoId(1L);
        reseña.setClienteId(1L);
        reseña.setPuntuacion(5);
        reseña.setComentario("Excelente producto");
    }

    @Test
    void obtenerResenaPorId_Existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(reseña));

        Reseña resultado = service.obtenerReseñaPorId(1L);

        assertNotNull(resultado);
        assertEquals(5, resultado.getPuntuacion());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void crearResena_Valida() {
        ReseñaDTO dto = new ReseñaDTO();
        dto.setProductoId(1L);
        dto.setClienteId(1L);
        dto.setPuntuacion(5);
        dto.setComentario("Excelente producto");

        when(repository.save(any(Reseña.class))).thenReturn(reseña);

        Reseña resultado = service.crearReseña(dto);

        assertNotNull(resultado);
        assertEquals(5, resultado.getPuntuacion());
        verify(repository, times(1)).save(any(Reseña.class));
    }

    @Test
    void obtenerResenaPorId_NoExistente() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.obtenerReseñaPorId(2L);
        });
    }
}
