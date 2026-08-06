package com.tp.foodstore.entity;

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
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "detalles_pedido")
public class DetallePedido extends Base {

    private int cantidad;

    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @ToString.Exclude
    private Producto producto;
}
