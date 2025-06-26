package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepositoryJPA extends JpaRepository<Marca, Long> {
    boolean existsByNombre(String nombre);
}
