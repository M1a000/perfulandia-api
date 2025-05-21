package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.UsuarioDTO;
import cl.perfulandia.catalogo.model.Usuario;
import cl.perfulandia.catalogo.repository.UsuarioRepositoryJPA;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositoryJPA repository;

    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setCorreo(u.getCorreo());
        dto.setContraseña(u.getContraseña());
        dto.setActivo(u.getActivo());
        return dto;
    }

    @Cacheable("usuarios")
    public List<UsuarioDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "usuario", key = "#id")
    public UsuarioDTO getById(Long id) {
        Usuario u = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + id));
        return toDTO(u);
    }

    @Cacheable(value = "usuariosActivos", key = "#activo")
    public List<UsuarioDTO> filterByActivo(Boolean activo) {
        return repository.findByActivo(activo).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO create(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setCorreo(dto.getCorreo());
        u.setContraseña(dto.getContraseña());
        u.setActivo(dto.getActivo());
        return toDTO(repository.save(u));
    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        Usuario u = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + id));
        u.setNombre(dto.getNombre());
        u.setCorreo(dto.getCorreo());
        u.setContraseña(dto.getContraseña());
        u.setActivo(dto.getActivo());
        return toDTO(repository.save(u));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}