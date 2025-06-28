package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReseñaRepositoryJPA extends JpaRepository<Reseña, Long> {
   
}
