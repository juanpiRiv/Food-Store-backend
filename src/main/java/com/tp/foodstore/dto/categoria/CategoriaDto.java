package com.tp.foodstore.dto.categoria;

import lombok.Data;

/**
 * DTO que representa una categoría.
 */
@Data
public class CategoriaDto {

    private Long id;
    private String nombre;
    private String descripcion;
}
