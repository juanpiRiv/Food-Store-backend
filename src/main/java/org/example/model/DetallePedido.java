package org.example.model;

import java.util.Objects;

public class DetallePedido {

    private int cantidad;
    private Double subTotal;
    private Producto producto;

    // cnstrct vacio
    public DetallePedido() {
    }

    // cnstrct principal
    public DetallePedido(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        actualizarSubTotal();
    }

    // Getters y setters
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        actualizarSubTotal();
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        actualizarSubTotal();
    }

    // Lógica interna
    private void actualizarSubTotal() {
        if (producto != null) {
            this.subTotal = producto.getPrecio() * cantidad;
        }
    }

    // toString
    @Override
    public String toString() {
        return "DetallePedido{" +
                "producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                '}';
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetallePedido that)) return false;
        return Objects.equals(producto, that.producto);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(producto);
    }
}