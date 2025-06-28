package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepositoryJPA extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuarioId(Long usuarioId);
}