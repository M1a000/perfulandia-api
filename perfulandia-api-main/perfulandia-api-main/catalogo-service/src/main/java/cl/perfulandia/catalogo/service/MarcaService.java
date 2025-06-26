package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.MarcaDTO;
import cl.perfulandia.catalogo.model.Marca;
import cl.perfulandia.catalogo.repository.MarcaRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepositoryJPA repository;

    // Métodos para controlador estándar (DTO)
    @Cacheable("marcas")
    public List<MarcaDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "marca", key = "#id")
    public MarcaDTO getById(Long id) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada: " + id));
        return toDTO(marca);
    }

    @Transactional
    public MarcaDTO create(MarcaDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una marca con ese nombre");
        }
        Marca marca = new Marca();
        marca.setNombre(dto.getNombre());
        marca.setDescripcion(dto.getDescripcion());
        marca.setActiva(dto.getActiva() != null ? dto.getActiva() : true);
        return toDTO(repository.save(marca));
    }

    @Transactional
    public MarcaDTO update(Long id, MarcaDTO dto) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada: " + id));
        
        if (!marca.getNombre().equals(dto.getNombre()) && repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una marca con ese nombre");
        }
        
        marca.setNombre(dto.getNombre());
        marca.setDescripcion(dto.getDescripcion());
        marca.setActiva(dto.getActiva() != null ? dto.getActiva() : true);
        return toDTO(repository.save(marca));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Métodos para controlador V2 (HATEOAS)
    public Marca obtenerMarcaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada: " + id));
    }

    public List<Marca> obtenerTodasMarcas() {
        return repository.findAll();
    }

    @Transactional
    public Marca crearMarca(MarcaDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una marca con ese nombre");
        }
        Marca marca = new Marca();
        marca.setNombre(dto.getNombre());
        marca.setDescripcion(dto.getDescripcion());
        marca.setActiva(dto.getActiva() != null ? dto.getActiva() : true);
        return repository.save(marca);
    }

    @Transactional
    public Marca actualizarMarca(Long id, MarcaDTO dto) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada: " + id));
        
        if (!marca.getNombre().equals(dto.getNombre()) && repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una marca con ese nombre");
        }
        
        marca.setNombre(dto.getNombre());
        marca.setDescripcion(dto.getDescripcion());
        marca.setActiva(dto.getActiva() != null ? dto.getActiva() : true);
        return repository.save(marca);
    }

    @Transactional
    public void eliminarMarca(Long id) {
        repository.deleteById(id);
    }

    // Método auxiliar
    private MarcaDTO toDTO(Marca marca) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(marca.getId());
        dto.setNombre(marca.getNombre());
        dto.setDescripcion(marca.getDescripcion());
        dto.setActiva(marca.getActiva());
        return dto;
    }
}
