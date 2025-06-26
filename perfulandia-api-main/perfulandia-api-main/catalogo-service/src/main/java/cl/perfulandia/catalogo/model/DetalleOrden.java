package cl.perfulandia.catalogo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalles_orden")
@Data
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ordenId;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer precioUnitario;
}