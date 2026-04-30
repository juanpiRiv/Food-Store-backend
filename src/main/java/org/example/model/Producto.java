package org.example.model;

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
public class Producto extends Base {

    @EqualsAndHashCode.Include
    private String nombre;

    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;

    public boolean tieneStock(int cantidad) {
        return stock >= cantidad;
    }

    public void descontarStock(int cantidad) {
        if (cantidad > 0 && stock >= cantidad) {
            stock -= cantidad;
        }
    }
}