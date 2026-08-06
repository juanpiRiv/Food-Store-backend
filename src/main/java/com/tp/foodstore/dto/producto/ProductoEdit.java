package com.tp.foodstore.dto.producto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para editar un producto.
 */
@Data
@Builder
public class ProductoEdit {

    private Long id;
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Long categoriaId;
}
