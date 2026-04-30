package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Categoria extends Base {

    @EqualsAndHashCode.Include
    private String nombre;

    private String descripcion;

    @Builder.Default
    @ToString.Exclude
    private Set<Producto> productos = new HashSet<>();

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }
}