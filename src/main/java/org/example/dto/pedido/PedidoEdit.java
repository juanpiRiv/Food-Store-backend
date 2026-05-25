package org.example.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Estado;
import org.example.enums.FormaPago;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEdit {

    private Estado estado;
    private FormaPago formaPago;
}