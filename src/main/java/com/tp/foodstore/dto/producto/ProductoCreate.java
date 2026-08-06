package com.tp.foodstore.dto.producto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para crear un producto.
 */
@Data
@Builder
public class ProductoCreate {

    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Long categoriaId;
}
