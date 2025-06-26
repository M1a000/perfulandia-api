package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepositoryJPA extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
    List<Categoria> findByActiva(Boolean activa);
}