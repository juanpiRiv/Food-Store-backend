package com.tp.foodstore.service.interfaces;

import com.tp.foodstore.dto.categoria.CategoriaCreate;
import com.tp.foodstore.dto.categoria.CategoriaDto;
import com.tp.foodstore.dto.categoria.CategoriaEdit;

import java.util.List;

/**
 * Servicio de categorías: crea, consulta, actualiza y elimina categorías.
 */
public interface CategoriaService {

    CategoriaDto crear(CategoriaCreate dto);

    CategoriaDto obtenerPorId(Long id);

    List<CategoriaDto> listar();

    CategoriaDto actualizar(Long id, CategoriaEdit dto);

    void eliminar(Long id);
}
