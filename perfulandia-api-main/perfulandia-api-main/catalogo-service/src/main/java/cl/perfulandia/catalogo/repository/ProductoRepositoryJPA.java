package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositoryJPA extends JpaRepository<Producto, Long> {
    boolean existsByNombre(String nombre);
}
