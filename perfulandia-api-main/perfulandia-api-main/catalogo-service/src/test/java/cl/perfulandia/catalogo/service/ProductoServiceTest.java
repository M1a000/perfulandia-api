package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.ProductoDTO;
import cl.perfulandia.catalogo.model.Producto;
import cl.perfulandia.catalogo.repository.ProductoRepositoryJPA;
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
public class ProductoServiceTest {

    @Mock
    private ProductoRepositoryJPA repository;

    @InjectMocks
    private ProductoService service;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Chanel N°5");
        producto.setPrecio(120.99);
        producto.setDescripcion("Fragancia clásica");
        producto.setActivo(true);
    }

    @Test
    void obtenerProductoPorId_Existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = service.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals("Chanel N°5", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void crearProducto_NombreUnico() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Dior J'adore");
        
        when(repository.existsByNombre("Dior J'adore")).thenReturn(false);
        when(repository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = service.crearProducto(dto);

        assertNotNull(resultado);
        assertEquals("Chanel N°5", resultado.getNombre());
        verify(repository, times(1)).existsByNombre("Dior J'adore");
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    void crearProducto_NombreDuplicado() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Chanel N°5");
        
        when(repository.existsByNombre("Chanel N°5")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            service.crearProducto(dto);
        });
    }
}
