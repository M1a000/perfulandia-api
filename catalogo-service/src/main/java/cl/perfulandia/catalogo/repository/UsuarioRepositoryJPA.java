package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepositoryJPA extends JpaRepository<Usuario, Long> {
    List<Usuario> findByActivo(Boolean activo);
}