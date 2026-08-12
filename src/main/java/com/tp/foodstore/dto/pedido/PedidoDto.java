package com.tp.foodstore.dto.pedido;

import com.tp.foodstore.dto.detallePedido.DetallePedidoDto;
import com.tp.foodstore.entity.enums.Estado;
import com.tp.foodstore.entity.enums.FormaPago;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO que representa un pedido.
 */
@Data
public class PedidoDto {

    private Long id;
    private LocalDateTime fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Long usuarioId;
    private List<DetallePedidoDto> detalles;
}
