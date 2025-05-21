package cl.perfulandia.catalogo.controller;

import lombok.Data;

@Data
public class MarcaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activa;
}
