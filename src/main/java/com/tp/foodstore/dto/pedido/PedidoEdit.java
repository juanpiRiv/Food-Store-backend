package com.tp.foodstore.dto.pedido;

import com.tp.foodstore.dto.detallePedido.DetallePedidoCreate;
import com.tp.foodstore.entity.enums.Estado;
import com.tp.foodstore.entity.enums.FormaPago;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para editar un pedido.
 */
@Data
@Builder
public class PedidoEdit {

    private LocalDateTime fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Long usuarioId;
    private List<DetallePedidoCreate> detalles;
}
