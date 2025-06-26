package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.ReseñaDTO;
import cl.perfulandia.catalogo.model.Reseña;
import cl.perfulandia.catalogo.repository.ReseñaRepositoryJPA;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReseñaService {

    @Autowired
    private ReseñaRepositoryJPA repository;

    private ReseñaDTO toDTO(Reseña r) {
        ReseñaDTO dto = new ReseñaDTO();
        dto.setId(r.getId());
        dto.setProductoId(r.getProductoId());
        dto.setUsuarioId(r.getUsuarioId());
        dto.setComentario(r.getComentario());
        dto.setPuntuacion(r.getPuntuacion());
        return dto;
    }

    @Cacheable("resenas")
    public List<ReseñaDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "resena", key = "#id")
    public ReseñaDTO getById(Long id) {
        Reseña r = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada: " + id));
        return toDTO(r);
    }

    @Cacheable(value = "resenasByProducto", key = "#productoId")
    public List<ReseñaDTO> filterByProducto(Long productoId) {
        return repository.findByProductoId(productoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ReseñaDTO create(ReseñaDTO dto) {
        Reseña r = new Reseña();
        r.setProductoId(dto.getProductoId());
        r.setUsuarioId(dto.getUsuarioId());
        r.setComentario(dto.getComentario());
        r.setPuntuacion(dto.getPuntuacion());
        return toDTO(repository.save(r));
    }

    @Transactional
    public ReseñaDTO update(Long id, ReseñaDTO dto) {
        Reseña r = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada: " + id));
        r.setProductoId(dto.getProductoId());
        r.setUsuarioId(dto.getUsuarioId());
        r.setComentario(dto.getComentario());
        r.setPuntuacion(dto.getPuntuacion());
        return toDTO(repository.save(r));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}