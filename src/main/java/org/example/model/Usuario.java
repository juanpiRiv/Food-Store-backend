package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.enums.Rol;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Usuario extends Base {

    private String nombre;
    private String apellido;

    @EqualsAndHashCode.Include
    private String email;

    private String celular;

    @ToString.Exclude
    private String contraseña;

    private Rol rol;

    @Builder.Default
    @ToString.Exclude
    private Set<Pedido> pedidos = new HashSet<>();

    public void agregarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidos.add(pedido);
        }
    }

    public int cantidadPedidos() {
        return pedidos.size();
    }
}