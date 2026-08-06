package com.tp.foodstore.dto.categoria;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para crear una categoría.
 */
@Data
@Builder
public class CategoriaCreate {

    private String nombre;
    private String descripcion;
}
