package cl.perfulandia.catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

@Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer stock;

@Column(nullable = false)
    private String categoria;
    private Boolean ecoFriendly;
}
