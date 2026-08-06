package com.tp.foodstore.dto.categoria;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para editar una categoría.
 */
@Data
@Builder
public class CategoriaEdit {

    private Long id;
    private String nombre;
    private String descripcion;
}
