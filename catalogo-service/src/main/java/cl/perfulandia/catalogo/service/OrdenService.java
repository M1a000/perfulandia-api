package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.OrdenDTO;
import cl.perfulandia.catalogo.model.Orden;
import cl.perfulandia.catalogo.repository.OrdenRepositoryJPA;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepositoryJPA repository;

    private OrdenDTO toDTO(Orden o) {
        OrdenDTO dto = new OrdenDTO();
        dto.setId(o.getId());
        dto.setUsuarioId(o.getUsuarioId());
        dto.setFecha(o.getFecha());
        dto.setTotal(o.getTotal());
        dto.setEstado(o.getEstado());
        return dto;
    }

    @Cacheable("ordenes")
    public List<OrdenDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "orden", key = "#id")
    public OrdenDTO getById(Long id) {
        Orden o = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Orden no encontrada: " + id));
        return toDTO(o);
    }

    @Cacheable(value = "ordenesByUsuario", key = "#usuarioId")
    public List<OrdenDTO> filterByUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrdenDTO create(OrdenDTO dto) {
        Orden o = new Orden();
        o.setUsuarioId(dto.getUsuarioId());
        o.setFecha(dto.getFecha());
        o.setTotal(dto.getTotal());
        o.setEstado(dto.getEstado());
        return toDTO(repository.save(o));
    }

    @Transactional
    public OrdenDTO update(Long id, OrdenDTO dto) {
        Orden o = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Orden no encontrada: " + id));
        o.setUsuarioId(dto.getUsuarioId());
        o.setFecha(dto.getFecha());
        o.setTotal(dto.getTotal());
        o.setEstado(dto.getEstado());
        return toDTO(repository.save(o));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}