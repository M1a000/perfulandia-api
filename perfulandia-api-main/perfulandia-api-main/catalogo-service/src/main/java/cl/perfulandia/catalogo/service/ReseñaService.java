package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.ReseñaDTO;
import cl.perfulandia.catalogo.model.Reseña;
import cl.perfulandia.catalogo.repository.ReseñaRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReseñaService {

    @Autowired
    private ReseñaRepositoryJPA repository;

    // Métodos para controlador estándar (DTO)
    public List<ReseñaDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ReseñaDTO getById(Long id) {
        Reseña reseña = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada: " + id));
        return toDTO(reseña);
    }

    @Transactional
    public ReseñaDTO create(ReseñaDTO dto) {
        Reseña reseña = new Reseña();
        reseña.setProductoId(dto.getProductoId());
        reseña.setClienteId(dto.getClienteId());
        reseña.setPuntuacion(dto.getPuntuacion());
        reseña.setComentario(dto.getComentario());
        return toDTO(repository.save(reseña));
    }

    @Transactional
    public ReseñaDTO update(Long id, ReseñaDTO dto) {
        Reseña reseña = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada: " + id));
        reseña.setProductoId(dto.getProductoId());
        reseña.setClienteId(dto.getClienteId());
        reseña.setPuntuacion(dto.getPuntuacion());
        reseña.setComentario(dto.getComentario());
        return toDTO(repository.save(reseña));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
    public Reseña obtenerReseñaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada: " + id));
    }

    public List<Reseña> obtenerTodasReseñas() {
        return repository.findAll();
    }

    @Transactional
    public Reseña crearReseña(ReseñaDTO dto) {
        Reseña reseña = new Reseña();
        reseña.setProductoId(dto.getProductoId());
        reseña.setClienteId(dto.getClienteId());
        reseña.setPuntuacion(dto.getPuntuacion());
        reseña.setComentario(dto.getComentario());
        return repository.save(reseña);
    }

    @Transactional
    public Reseña actualizarReseña(Long id, ReseñaDTO dto) {
        Reseña reseña = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada: " + id));
        reseña.setProductoId(dto.getProductoId());
        reseña.setClienteId(dto.getClienteId());
        reseña.setPuntuacion(dto.getPuntuacion());
        reseña.setComentario(dto.getComentario());
        return repository.save(reseña);
    }

    @Transactional
    public void eliminarReseña(Long id) {
        repository.deleteById(id);
    }

    
    private ReseñaDTO toDTO(Reseña reseña) {
        ReseñaDTO dto = new ReseñaDTO();
        dto.setId(reseña.getId());
        dto.setProductoId(reseña.getProductoId());
        dto.setClienteId(reseña.getClienteId());
        dto.setPuntuacion(reseña.getPuntuacion());
        dto.setComentario(reseña.getComentario());
        return dto;
    }
}
