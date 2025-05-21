package cl.perfulandia.catalogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cl.perfulandia.catalogo.controller.MarcaDTO;
import cl.perfulandia.catalogo.model.Marca;
import cl.perfulandia.catalogo.repository.MarcaRepositoryJPA;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepositoryJPA repository;

    private MarcaDTO toDTO(Marca m) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setDescripcion(m.getDescripcion());
        dto.setActiva(m.getActiva());
        return dto;
    }

    @Cacheable("marcas")
    public List<MarcaDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "marca", key = "#id")
    public MarcaDTO getById(Long id) {
        Marca m = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada: " + id));
        return toDTO(m);
    }

    @Cacheable(value = "marcasByNombre", key = "#nombre")
    public List<MarcaDTO> searchByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "marcasActivas", key = "#activa")
    public List<MarcaDTO> filterByActiva(Boolean activa) {
        return repository.findByActiva(activa).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public MarcaDTO create(MarcaDTO dto) {
        Marca m = new Marca();
        m.setNombre(dto.getNombre());
        m.setDescripcion(dto.getDescripcion());
        m.setActiva(dto.getActiva());
        return toDTO(repository.save(m));
    }

    @Transactional
    public MarcaDTO update(Long id, MarcaDTO dto) {
        Marca m = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada: " + id));
        m.setNombre(dto.getNombre());
        m.setDescripcion(dto.getDescripcion());
        m.setActiva(dto.getActiva());
        return toDTO(repository.save(m));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}