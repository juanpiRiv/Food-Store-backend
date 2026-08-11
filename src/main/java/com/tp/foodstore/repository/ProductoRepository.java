package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Producto;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de la entidad Producto.
 */
@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {
}
