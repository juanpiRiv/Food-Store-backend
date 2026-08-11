package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Categoria;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de la entidad Categoria.
 */
@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {
}
