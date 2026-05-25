package org.example.dto.detallePedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDto {

    private Long id;
    private int cantidad;
    private Double subTotal;
    private Long productoId;
    private String productoNombre;
}