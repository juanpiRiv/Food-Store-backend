package com.tp.foodstore.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * DTO para crear una categoría.
 */
@Data
@Builder
public class CategoriaCreate {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;
}
