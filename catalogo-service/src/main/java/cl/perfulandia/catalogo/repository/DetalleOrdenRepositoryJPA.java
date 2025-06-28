package cl.perfulandia.catalogo.repository;

import cl.perfulandia.catalogo.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleOrdenRepositoryJPA extends JpaRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrdenId(Long ordenId);
}