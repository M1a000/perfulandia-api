package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepositoryJPA extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
    List<Categoria> findByActiva(Boolean activa);
    
    // Método necesario para validar existencia por nombre
    boolean existsByNombre(String nombre);
}
