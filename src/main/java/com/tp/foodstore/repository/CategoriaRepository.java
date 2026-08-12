package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a datos de la entidad Categoria.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
