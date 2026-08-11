package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Pedido;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de la entidad Pedido.
 */
@Repository
public interface PedidoRepository extends BaseRepository<Pedido, Long> {
}
