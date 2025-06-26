package cl.perfulandia.catalogo.controller;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DetalleOrdenDTO {

    @Null(message = "El ID debe ser nulo en creación", groups = CreateValidation.class)
    @NotNull(message = "El ID es requerido para actualización", groups = UpdateValidation.class)
    private Long id;

    @NotNull(message = "El ID de orden es obligatorio")
    @Positive(message = "El ID de orden debe ser un número positivo")
    private Long ordenId;

    @NotNull(message = "El ID de producto es obligatorio")
    @Positive(message = "El ID de producto debe ser un número positivo")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Max(value = 100, message = "La cantidad máxima es 100")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio mínimo es 0.01")
    @DecimalMax(value = "10000.00", message = "El precio máximo es 10000.00")
    private Double precioUnitario;

    public interface CreateValidation {}
    public interface UpdateValidation {}
}
