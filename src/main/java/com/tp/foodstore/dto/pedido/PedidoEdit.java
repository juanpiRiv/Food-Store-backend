package com.tp.foodstore.dto.pedido;

import com.tp.foodstore.dto.detallePedido.DetallePedidoCreate;
import com.tp.foodstore.entity.enums.Estado;
import com.tp.foodstore.entity.enums.FormaPago;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para crear un pedido.
 */
@Data
@Builder
public class PedidoEdit {

    private LocalDateTime fecha;

    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

    private Double total;

    @NotNull(message = "La forma de pago es obligatoria")
    private FormaPago formaPago;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoCreate> detalles;
}
