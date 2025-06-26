package cl.perfulandia.catalogo.controller;

import lombok.Data;

@Data
public class ReseñaDTO {
    private Long id;
    private Long productoId;
    private Long usuarioId;
    private String comentario;
    private Integer puntuacion;
}