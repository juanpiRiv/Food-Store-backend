package com.tp.jpa.model;

import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "pedidos")
public class Pedido extends Base implements Calculable {

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetallePedido> detalles = new HashSet<>();

    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(cantidad);
        detalle.setProducto(producto);
        detalle.setSubtotal(cantidad * producto.getPrecio());
        detalle.setEliminado(false);
        detalles.add(detalle);
    }

    @Override
    public void calcularTotal() {
        total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }
}
