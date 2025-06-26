package cl.perfulandia.catalogo.service;

import cl.perfulandia.catalogo.controller.ProductoDTO;
import cl.perfulandia.catalogo.model.Producto;
import cl.perfulandia.catalogo.repository.ProductoRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepositoryJPA repository;

    // Métodos para controlador estándar (DTO)
    public List<ProductoDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProductoDTO getById(Long id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        return toDTO(producto);
    }

    @Transactional
    public ProductoDTO create(ProductoDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return toDTO(repository.save(producto));
    }

    @Transactional
    public ProductoDTO update(Long id, ProductoDTO dto) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        if (!producto.getNombre().equals(dto.getNombre()) && repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return toDTO(repository.save(producto));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Métodos para controlador V2 (HATEOAS)
    public Producto obtenerProductoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
    }

    public List<Producto> obtenerTodosProductos() {
        return repository.findAll();
    }

    @Transactional
    public Producto crearProducto(ProductoDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return repository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        if (!producto.getNombre().equals(dto.getNombre()) && repository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true); // ¡OJO! Error tipográfico: debe ser dto.getActivo()
        return repository.save(producto);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        repository.deleteById(id);
    }

    // Método auxiliar
    private ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setDescripcion(producto.getDescripcion());
        dto.setActivo(producto.getActivo());
        return dto;
    }
}
