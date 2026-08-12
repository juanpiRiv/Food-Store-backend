package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a datos de la entidad Pedido.
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
