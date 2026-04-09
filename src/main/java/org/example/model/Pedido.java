package org.example.model;

import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.interfaces.Calculable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Set<DetallePedido> detalles;

    // cstr vacio
    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.total = 0.0;
        this.detalles = new HashSet<>();
    }

    // cstr con parametros
    public Pedido(Long id, LocalDate fecha, Estado estado, FormaPago formaPago) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.total = 0.0;
        this.detalles = new HashSet<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetallePedido> detalles) {
        this.detalles = detalles;
        recalcularTotal();
    }

    public void addDetallePedido(int cantidad, Producto producto) {
        if (producto != null && cantidad > 0) {
            DetallePedido detalle = new DetallePedido(cantidad, producto);
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

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                ", formaPago=" + formaPago +
                ", cantidadDetalles=" + detalles.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido pedido)) return false;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}