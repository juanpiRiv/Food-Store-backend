package com.tp.foodstore.service.interfaces;

import com.tp.foodstore.dto.pedido.PedidoDto;
import com.tp.foodstore.dto.pedido.PedidoEdit;

import java.util.List;

/**
 * Servicio de pedidos: crea y consulta pedidos.
 */
public interface PedidoService {

    PedidoDto crear(PedidoEdit dto);

    PedidoDto obtenerPorId(Long id);

    List<PedidoDto> listar();
}
