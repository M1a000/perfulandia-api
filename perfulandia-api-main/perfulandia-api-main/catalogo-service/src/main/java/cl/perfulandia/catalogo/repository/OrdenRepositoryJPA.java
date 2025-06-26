package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepositoryJPA extends JpaRepository<Orden, Long> {
    // Puedes agregar métodos personalizados aquí si lo necesitas
}
