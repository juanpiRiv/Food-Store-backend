package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedidos")
public class Pedido extends Base implements Calculable {

    @EqualsAndHashCode.Include
    private LocalDate fecha;

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total;

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @ToString.Exclude
    private Usuario usuario;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

    public void addDetallePedido(int cantidad, Producto producto) {
        if (producto != null && cantidad > 0) {
            DetallePedido detalle = DetallePedido.builder()
                    .cantidad(cantidad)
                    .producto(producto)
                    .subTotal(producto.getPrecio() * cantidad)
                    .build();

            detalle.setPedido(this);
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
        return detalles.stream()
                .mapToDouble(DetallePedido::getSubTotal)
                .sum();
    }

    public int calcularCantidadItems() {
        return detalles.stream()
                .mapToInt(DetallePedido::getCantidad)
                .sum();
    }

    private void recalcularTotal() {
        this.total = calcularTotal();
    }
}