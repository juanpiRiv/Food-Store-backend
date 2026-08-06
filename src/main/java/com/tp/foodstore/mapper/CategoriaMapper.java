package com.tp.foodstore.mapper;

import com.tp.foodstore.dto.categoria.CategoriaCreate;
import com.tp.foodstore.dto.categoria.CategoriaDto;
import com.tp.foodstore.entity.Categoria;
import org.springframework.stereotype.Component;

/**
 * Convierte DTOs de categorías en la entidad Categoria y viceversa.
 */
@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaCreate dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }

    public CategoriaDto toDto(Categoria categoria) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
