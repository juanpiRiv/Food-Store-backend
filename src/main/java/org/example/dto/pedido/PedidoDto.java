package org.example.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.dto.detallePedido.DetallePedidoDto;
import org.example.enums.Estado;
import org.example.enums.FormaPago;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

    private Long id;
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Long usuarioId;
    private String usuarioEmail;
    private List<DetallePedidoDto> detalles;
}