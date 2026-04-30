package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetallePedido {

    private int cantidad;
    private Double subTotal;

    @EqualsAndHashCode.Include
    private Producto producto;

    public void actualizarSubTotal() {
        if (producto != null) {
            this.subTotal = producto.getPrecio() * cantidad;
        }
    }
}