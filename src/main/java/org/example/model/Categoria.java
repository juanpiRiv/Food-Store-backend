package org.example.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Categoria extends Base {

    private String nombre;
    private String descripcion;
    private Set<Producto> productos;

    // cstrctor vacío
    public Categoria() {
        super();
        this.productos = new HashSet<>();
    }

    // cstrctor con params
    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new HashSet<>();
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    // metodos de negocio
    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    // toString
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidadProductos=" + productos.size() +
                '}';
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria categoria)) return false;
        return Objects.equals(nombre, categoria.nombre);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}