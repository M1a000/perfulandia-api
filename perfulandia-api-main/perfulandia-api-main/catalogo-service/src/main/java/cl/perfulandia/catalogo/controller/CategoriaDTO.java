package cl.perfulandia.catalogo.controller;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoriaDTO {

    @Null(message = "El ID debe ser nulo en creación", groups = CreateValidation.class)
    @NotNull(message = "El ID es requerido para actualización", groups = UpdateValidation.class)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$", 
             message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @Size(max = 500, message = "La descripción debe tener máximo 500 caracteres")
    private String descripcion;

    private Boolean activa = true;

    public interface CreateValidation {}
    public interface UpdateValidation {}
}
