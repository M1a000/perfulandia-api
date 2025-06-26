package cl.perfulandia.catalogo.controller;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReseñaDTO {

    @Null(message = "El ID debe ser nulo en creación", groups = CreateValidation.class)
    @NotNull(message = "El ID es requerido para actualización", groups = UpdateValidation.class)
    private Long id;

    @NotNull(message = "El ID de producto es obligatorio")
    @Positive(message = "El ID de producto debe ser un número positivo")
    private Long productoId;

    @NotNull(message = "El ID de cliente es obligatorio")
    @Positive(message = "El ID de cliente debe ser un número positivo")
    private Long clienteId;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer puntuacion;

    @Size(max = 1000, message = "El comentario debe tener máximo 1000 caracteres")
    private String comentario;

    public interface CreateValidation {}
    public interface UpdateValidation {}
}
