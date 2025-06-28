package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReseñaRepositoryJPA extends JpaRepository<Reseña, Long> {
    List<Reseña> findByProductoId(Long productoId);
}