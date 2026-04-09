package org.example;

import org.example.data.DataSeeder;
import org.example.model.Pedido;
import org.example.model.Producto;
import org.example.model.Usuario;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<Producto> productos = DataSeeder.crearProductos();
        Set<Usuario> usuarios = DataSeeder.crearUsuarios(productos);
        DataSeeder.crearCategorias(productos);

        mostrarUnProducto(productos);
        mostrarTodosLosProductos(productos);
        mostrarPedidosDelUsuarioConMasPedidos(usuarios);
        compararProductoNuevoConColeccion(productos);
    }

    public static void mostrarUnProducto(Set<Producto> productos) {
        System.out.println("=== UN PRODUCTO ===");
        for (Producto producto : productos) {
            System.out.println(producto);
            break;
        }
        System.out.println();
    }

    public static void mostrarTodosLosProductos(Set<Producto> productos) {
        System.out.println("=== LISTADO DE PRODUCTOS ===");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
        System.out.println();
    }

    public static void mostrarPedidosDelUsuarioConMasPedidos(Set<Usuario> usuarios) {
        System.out.println("=== PEDIDOS DEL USUARIO CON MÁS PEDIDOS ===");

        Usuario usuarioConMasPedidos = null;
        int maxPedidos = -1;

        for (Usuario usuario : usuarios) {
            if (usuario.getPedidos().size() > maxPedidos) {
                maxPedidos = usuario.getPedidos().size();
                usuarioConMasPedidos = usuario;
            }
        }

        if (usuarioConMasPedidos != null) {
            System.out.println("Usuario: " + usuarioConMasPedidos.getNombre() + " " + usuarioConMasPedidos.getApellido());
            System.out.println("Cantidad de pedidos: " + usuarioConMasPedidos.getPedidos().size());

            for (Pedido pedido : usuarioConMasPedidos.getPedidos()) {
                System.out.println(pedido);
            }
        }

        System.out.println();
    }

    public static void compararProductoNuevoConColeccion(Set<Producto> productos) {
        System.out.println("=== COMPARACIÓN DE PRODUCTO NUEVO CON LA COLECCIÓN ===");

        Producto productoNuevo = new Producto(
                99L,
                "Coca",
                2000.0,
                "Producto nuevo para comparar",
                5,
                "coca_nueva.jpg",
                true
        );

        System.out.println("Producto nuevo: " + productoNuevo);
        System.out.println();

        for (Producto producto : productos) {
            System.out.println("Comparando con: " + producto.getNombre() + " -> " + producto.equals(productoNuevo));
        }

        System.out.println();
    }
}