package cl.perfulandia.catalogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cl.perfulandia.catalogo.controller.ProductoDTO;
import cl.perfulandia.catalogo.model.Producto;
import cl.perfulandia.catalogo.repository.ProductoRepositoryJPA;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductoService {
    @Autowired
    private ProductoRepositoryJPA repository;

    private ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        dto.setCategoria(p.getCategoria());
        dto.setEcoFriendly(p.getEcoFriendly());
        return dto;
    }

    @Cacheable("products")
    public List<ProductoDTO> getAllProductDtos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "producto", key = "#id")
    public ProductoDTO getProductById(Long id) {
        Producto p = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        return toDTO(p);
    }

    @Cacheable(value = "productsByName", key = "#nombre")
    public List<ProductoDTO> searchProductsByName(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(value = "productsByCategoria", key = "#categoria")
    public List<ProductoDTO> filterProductsByCategory(String categoria) {
        return repository.findByCategoria(categoria).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ProductoDTO createProduct(ProductoDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(dto.getCategoria());
        p.setEcoFriendly(dto.getEcoFriendly());
        return toDTO(repository.save(p));
    }

    @Transactional
    public ProductoDTO updateProduct(Long id, ProductoDTO dto) {
        Producto p = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + id));
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(dto.getCategoria());
        p.setEcoFriendly(dto.getEcoFriendly());
        return toDTO(repository.save(p));
    }

    @Transactional
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
