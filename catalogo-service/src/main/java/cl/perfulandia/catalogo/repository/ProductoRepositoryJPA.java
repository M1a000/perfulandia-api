package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepositoryJPA extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByCategoria(String categoria);
}
