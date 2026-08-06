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
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "productos")
public class Producto extends Base {

    @EqualsAndHashCode.Include
    private String nombre;

    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @ToString.Exclude
    private Categoria categoria;

    public boolean tieneStock(int cantidad) {
        return stock >= cantidad;
    }

    public void descontarStock(int cantidad) {
        if (cantidad > 0 && stock >= cantidad) {
            stock -= cantidad;
        }
    }
}
