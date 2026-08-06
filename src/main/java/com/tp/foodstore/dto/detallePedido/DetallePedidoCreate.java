package com.tp.foodstore.dto.detallePedido;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para crear un detalle de pedido.
 */
@Data
@Builder
public class DetallePedidoCreate {

    private int cantidad;
    private Double subtotal;
}
