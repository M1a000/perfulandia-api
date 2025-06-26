package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.DetalleOrdenDTO;
import cl.perfulandia.catalogo.model.DetalleOrden;
import cl.perfulandia.catalogo.repository.DetalleOrdenRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepositoryJPA repository;

    // Métodos para HATEOAS
    public DetalleOrden obtenerDetalleOrdenPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleOrden no encontrado: " + id));
    }

    public List<DetalleOrden> obtenerTodosDetallesOrden() {
        return repository.findAll();
    }

    @Transactional
    public DetalleOrden crearDetalleOrden(DetalleOrdenDTO dto) {
        DetalleOrden d = new DetalleOrden();
        d.setOrdenId(dto.getOrdenId());
        d.setProductoId(dto.getProductoId());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        return repository.save(d);
    }

    @Transactional
    public DetalleOrden actualizarDetalleOrden(Long id, DetalleOrdenDTO dto) {
        DetalleOrden d = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleOrden no encontrado: " + id));
        d.setOrdenId(dto.getOrdenId());
        d.setProductoId(dto.getProductoId());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        return repository.save(d);
    }

    @Transactional
    public void eliminarDetalleOrden(Long id) {
        repository.deleteById(id);
    }

    // Métodos para DTO
    private DetalleOrdenDTO toDTO(DetalleOrden d) {
        DetalleOrdenDTO dto = new DetalleOrdenDTO();
        dto.setId(d.getId());
        dto.setOrdenId(d.getOrdenId());
        dto.setProductoId(d.getProductoId());
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        return dto;
    }

    @Cacheable("detallesOrden")
    public List<DetalleOrdenDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "detalleOrden", key = "#id")
    public DetalleOrdenDTO getById(Long id) {
        DetalleOrden d = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DetalleOrden no encontrado: " + id));
        return toDTO(d);
    }

    @Cacheable(value = "detallesByOrden", key = "#ordenId")
    public List<DetalleOrdenDTO> filterByOrden(Long ordenId) {
        return repository.findByOrdenId(ordenId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public DetalleOrdenDTO create(DetalleOrdenDTO dto) {
        DetalleOrden d = new DetalleOrden();
        d.setOrdenId(dto.getOrdenId());
        d.setProductoId(dto.getProductoId());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        return toDTO(repository.save(d));
    }

    @Transactional
    public DetalleOrdenDTO update(Long id, DetalleOrdenDTO dto) {
        DetalleOrden d = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("DetalleOrden no encontrado: " + id));
        d.setOrdenId(dto.getOrdenId());
        d.setProductoId(dto.getProductoId());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        return toDTO(repository.save(d));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
