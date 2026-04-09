package org.example.model;

import org.example.enums.Rol;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String contraseña;
    private Rol rol;
    private Set<Pedido> pedidos;

    // Crstr vacío
    public Usuario() {
        super();
        this.pedidos = new HashSet<>();
    }

    // Crstr completo
    public Usuario(Long id, String nombre, String apellido, String email, String celular, String contraseña, Rol rol) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
        this.pedidos = new HashSet<>();
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    // metodo pedido
    public void agregarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidos.add(pedido);
        }
    }

    // utils
    public int cantidadPedidos() {
        return pedidos.size();
    }

    // toString
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", rol=" + rol +
                ", cantidadPedidos=" + pedidos.size() +
                '}';
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(email, usuario.email);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}