package com.tp.foodstore.dto.producto;

import lombok.Data;

/**
 * DTO que representa un producto.
 */
@Data
public class ProductoDto {

    private Long id;
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
}
