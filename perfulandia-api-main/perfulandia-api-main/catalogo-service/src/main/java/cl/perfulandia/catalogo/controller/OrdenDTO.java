package cl.perfulandia.catalogo.controller;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrdenDTO {

    @Null(message = "El ID debe ser nulo en creación", groups = CreateValidation.class)
    @NotNull(message = "El ID es requerido para actualización", groups = UpdateValidation.class)
    private Long id;

    @NotNull(message = "El ID de cliente es obligatorio")
    @Positive(message = "El ID de cliente debe ser un número positivo")
    private Long clienteId;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    public interface CreateValidation {}
    public interface UpdateValidation {}
}
