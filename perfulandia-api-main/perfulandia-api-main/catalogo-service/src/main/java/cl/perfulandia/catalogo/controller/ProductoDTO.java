package cl.perfulandia.catalogo.controller;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductoDTO {

    @Null(message = "El ID debe ser nulo en creación", groups = CreateValidation.class)
    @NotNull(message = "El ID es requerido para actualización", groups = UpdateValidation.class)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio mínimo es 0.01")
    @DecimalMax(value = "10000.00", message = "El precio máximo es 10000.00")
    private Double precio;

    @Size(max = 500, message = "La descripción debe tener máximo 500 caracteres")
    private String descripcion;

    private Boolean activo = true;

    public interface CreateValidation {}
    public interface UpdateValidation {}
}
