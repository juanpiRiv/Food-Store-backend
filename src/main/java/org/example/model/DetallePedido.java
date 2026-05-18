package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "detalles_pedido")
public class DetallePedido extends Base {

    private int cantidad;

    @Column(name = "sub_total")
    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @ToString.Exclude
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @ToString.Exclude
    private Producto producto;

    public void actualizarSubTotal() {
        if (producto != null) {
            this.subTotal = producto.getPrecio() * cantidad;
        }
    }
}