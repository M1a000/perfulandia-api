package cl.perfulandia.catalogo.controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrdenDTO {
    private Long id;
    private Long usuarioId;
    private LocalDate fecha;
    private Integer total;
    private String estado;
}
