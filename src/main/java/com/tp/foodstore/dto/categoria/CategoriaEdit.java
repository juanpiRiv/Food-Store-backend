package com.tp.foodstore.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para editar una categoría.
 */
@Data
public class CategoriaEdit {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;
}
