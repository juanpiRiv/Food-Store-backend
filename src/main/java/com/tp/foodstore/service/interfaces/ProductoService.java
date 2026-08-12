package com.tp.foodstore.service.interfaces;

import com.tp.foodstore.dto.producto.ProductoCreate;
import com.tp.foodstore.dto.producto.ProductoDto;
import com.tp.foodstore.dto.producto.ProductoEdit;

import java.util.List;

/**
 * Servicio de productos: crea, consulta, actualiza y elimina productos.
 */
public interface ProductoService {

    ProductoDto crear(ProductoCreate dto);

    ProductoDto obtenerPorId(Long id);

    List<ProductoDto> listar();

    ProductoDto actualizar(Long id, ProductoEdit dto);

    void eliminar(Long id);
}
