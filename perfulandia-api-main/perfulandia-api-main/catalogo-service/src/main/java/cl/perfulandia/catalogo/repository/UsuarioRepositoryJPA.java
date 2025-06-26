package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositoryJPA extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}
