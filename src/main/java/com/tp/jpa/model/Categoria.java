package com.tp.jpa.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categorias")
public class Categoria extends Base {

    @EqualsAndHashCode.Include
    private String nombre;

    private String descripcion;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private Set<Producto> productos = new HashSet<>();

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
            producto.setCategoria(this);
        }
    }

    public void eliminarProducto(Producto producto) {
        if (producto != null) {
            productos.remove(producto);
            producto.setCategoria(null);
        }
    }
}
