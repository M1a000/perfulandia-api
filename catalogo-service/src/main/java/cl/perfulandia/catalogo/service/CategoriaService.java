package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.CategoriaDTO;
import cl.perfulandia.catalogo.model.Categoria;
import cl.perfulandia.catalogo.repository.CategoriaRepositoryJPA;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepositoryJPA repository;

    private CategoriaDTO toDTO(Categoria c) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setActiva(c.getActiva());
        return dto;
    }
    @Cacheable("categorias")
    public List<CategoriaDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "categoria", key = "#id")
    public CategoriaDTO getById(Long id) {
        Categoria c = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada: " + id));
        return toDTO(c);
    }

    @Cacheable(value = "categoriasByNombre", key = "#nombre")
    public List<CategoriaDTO> searchByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "categoriasActivas", key = "#activa")
    public List<CategoriaDTO> filterByActiva(Boolean activa) {
        return repository.findByActiva(activa).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public CategoriaDTO create(CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());
        c.setActiva(dto.getActiva());
        return toDTO(repository.save(c));
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria c = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada: " + id));
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());
        c.setActiva(dto.getActiva());
        return toDTO(repository.save(c));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}