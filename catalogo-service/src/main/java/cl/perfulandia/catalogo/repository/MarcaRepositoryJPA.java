package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepositoryJPA extends JpaRepository<Marca, Long> {
    List<Marca> findByNombreContainingIgnoreCase(String nombre);
    List<Marca> findByActiva(Boolean activa);
}