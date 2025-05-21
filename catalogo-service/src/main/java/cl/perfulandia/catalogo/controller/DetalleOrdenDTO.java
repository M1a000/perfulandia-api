package cl.perfulandia.catalogo.controller;

import lombok.Data;

@Data
public class DetalleOrdenDTO {
    private Long id;
    private Long ordenId;
    private Long productoId;
    private Integer cantidad;
    private Integer precioUnitario;
}