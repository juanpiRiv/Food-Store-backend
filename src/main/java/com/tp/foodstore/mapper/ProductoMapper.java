package com.tp.foodstore.mapper;

import com.tp.foodstore.dto.producto.ProductoCreate;
import com.tp.foodstore.dto.producto.ProductoDto;
import com.tp.foodstore.dto.producto.ProductoEdit;
import com.tp.foodstore.entity.Categoria;
import com.tp.foodstore.entity.Producto;
import org.springframework.stereotype.Component;

/**
 * Convierte DTOs de productos en la entidad Producto y viceversa.
 */
@Component
public class ProductoMapper {

    public Producto toEntity(ProductoCreate dto, Categoria categoria) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setImagen(dto.getImagen());
        producto.setDisponible(dto.isDisponible());
        producto.setCategoria(categoria);
        return producto;
    }

    public ProductoDto toDto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setDescripcion(producto.getDescripcion());
        dto.setStock(producto.getStock());
        dto.setImagen(producto.getImagen());
        dto.setDisponible(producto.isDisponible());
        return dto;
    }

    /**
     * Aplica los datos editables del DTO sobre un producto existente.
     */
    public void actualizar(Producto producto, ProductoEdit dto, Categoria categoria) {
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setImagen(dto.getImagen());
        producto.setDisponible(dto.isDisponible());
        producto.setCategoria(categoria);
    }
}
