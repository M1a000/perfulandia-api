package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.OrdenDTO;
import cl.perfulandia.catalogo.model.Orden;
import cl.perfulandia.catalogo.repository.OrdenRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepositoryJPA repository;

    // Métodos para controlador estándar (DTO)
    public List<OrdenDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public OrdenDTO getById(Long id) {
        Orden orden = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada: " + id));
        return toDTO(orden);
    }

    @Transactional
    public OrdenDTO create(OrdenDTO dto) {
        Orden orden = new Orden();
        orden.setClienteId(dto.getClienteId());
        orden.setEstado(dto.getEstado());
        return toDTO(repository.save(orden));
    }

    @Transactional
    public OrdenDTO update(Long id, OrdenDTO dto) {
        Orden orden = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada: " + id));
        orden.setClienteId(dto.getClienteId());
        orden.setEstado(dto.getEstado());
        return toDTO(repository.save(orden));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Métodos para controlador V2 (HATEOAS)
    public Orden obtenerOrdenPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada: " + id));
    }

    public List<Orden> obtenerTodasOrdenes() {
        return repository.findAll();
    }

    @Transactional
    public Orden crearOrden(OrdenDTO dto) {
        Orden orden = new Orden();
        orden.setClienteId(dto.getClienteId());
        orden.setEstado(dto.getEstado());
        return repository.save(orden);
    }

    @Transactional
    public Orden actualizarOrden(Long id, OrdenDTO dto) {
        Orden orden = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada: " + id));
        orden.setClienteId(dto.getClienteId());
        orden.setEstado(dto.getEstado());
        return repository.save(orden);
    }

    @Transactional
    public void eliminarOrden(Long id) {
        repository.deleteById(id);
    }

    // Método auxiliar
    private OrdenDTO toDTO(Orden orden) {
        OrdenDTO dto = new OrdenDTO();
        dto.setId(orden.getId());
        dto.setClienteId(orden.getClienteId());
        dto.setEstado(orden.getEstado()); // ¡OJO! Hay un error tipográfico: debe ser dto.setEstado(...)
        return dto;
    }
}
