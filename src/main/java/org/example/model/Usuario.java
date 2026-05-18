package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Entity
@Table(name = "usuarios")
public class Usuario extends Base {

    private String nombre;
    private String apellido;

    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true)
    private String email;

    private String celular;

    @ToString.Exclude
    @Column(name = "contrasenia")
    private String contraseña;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pedido> pedidos = new HashSet<>();

    public void agregarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidos.add(pedido);
            pedido.setUsuario(this);
        }
    }

    public int cantidadPedidos() {
        return pedidos.size();
    }
}