package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de Producto.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
