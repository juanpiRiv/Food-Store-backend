package com.tp.foodstore.dto.detallePedido;

import lombok.Data;

/**
 * DTO que representa un detalle de pedido.
 */
@Data
public class DetallePedidoDto {

    private Long id;
    private int cantidad;
    private Double subtotal;
    private Long productoId;
    private String nombreProducto;
}
