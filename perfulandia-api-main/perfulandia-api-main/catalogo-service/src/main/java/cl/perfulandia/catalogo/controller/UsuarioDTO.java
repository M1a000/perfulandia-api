package cl.perfulandia.catalogo.controller;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String contraseña;
    private Boolean activo;
}