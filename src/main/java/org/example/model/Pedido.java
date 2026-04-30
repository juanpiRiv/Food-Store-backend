package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.interfaces.Calculable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;

    @Builder.Default
    private Set<DetallePedido> detalles = new HashSet<>();

    public void addDetallePedido(int cantidad, Producto producto) {
        if (producto != null && cantidad > 0) {
            DetallePedido detalle = DetallePedido.builder()
                    .cantidad(cantidad)
                    .producto(producto)
                    .subTotal(producto.getPrecio() * cantidad)
                    .build();

            detalles.add(detalle);
            recalcularTotal();
        }
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }

        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        detalles.removeIf(detalle -> detalle.getProducto().equals(producto));
        recalcularTotal();
    }

    @Override
    public double calcularTotal() {
        double suma = 0.0;

        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubTotal();
        }

        return suma;
    }

    private void recalcularTotal() {
        this.total = calcularTotal();
    }
}