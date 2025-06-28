package cl.perfulandia.catalogo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resenas")
@Data
public class Reseña {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private Integer puntuacion; // escala 1-5
}