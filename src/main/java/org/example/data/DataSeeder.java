package org.example.data;

import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.Categoria;
import org.example.model.Pedido;
import org.example.model.Producto;
import org.example.model.Usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DataSeeder {

    public static Set<Producto> crearProductos() {
        Set<Producto> productos = new HashSet<>();

        productos.add(new Producto(1L, "Coca", 1000.0, "Gaseosa cola", 10, "coca.jpg", true));
        productos.add(new Producto(2L, "Pepsi", 1100.0, "Gaseosa cola", 8, "pepsi.jpg", true));
        productos.add(new Producto(3L, "Agua", 800.0, "Agua mineral", 20, "agua.jpg", true));
        productos.add(new Producto(4L, "Jugo", 950.0, "Jugo de naranja", 15, "jugo.jpg", true));
        productos.add(new Producto(5L, "Papas", 1200.0, "Papas fritas", 12, "papas.jpg", true));
        productos.add(new Producto(6L, "Galletas", 900.0, "Galletitas dulces", 18, "galletas.jpg", true));
        productos.add(new Producto(7L, "Chocolate", 1500.0, "Chocolate en barra", 7, "chocolate.jpg", true));
        productos.add(new Producto(8L, "Lavandina", 1300.0, "Lavandina 1L", 9, "lavandina.jpg", true));
        productos.add(new Producto(9L, "Detergente", 1400.0, "Detergente líquido", 11, "detergente.jpg", true));
        productos.add(new Producto(10L, "Esponja", 500.0, "Esponja multiuso", 25, "esponja.jpg", true));

        return productos;
    }

    public static Set<Categoria> crearCategorias(Set<Producto> productos) {
        Categoria bebidas = new Categoria(1L, "Bebidas", "Bebidas frías y calientes");
        Categoria snacks = new Categoria(2L, "Snacks", "Papas, galletas y golosinas");
        Categoria limpieza = new Categoria(3L, "Limpieza", "Productos de limpieza del hogar");

        for (Producto producto : productos) {
            switch (producto.getNombre()) {
                case "Coca", "Pepsi", "Agua", "Jugo" -> bebidas.agregarProducto(producto);
                case "Papas", "Galletas", "Chocolate" -> snacks.agregarProducto(producto);
                case "Lavandina", "Detergente", "Esponja" -> limpieza.agregarProducto(producto);
            }
        }

        Set<Categoria> categorias = new HashSet<>();
        categorias.add(bebidas);
        categorias.add(snacks);
        categorias.add(limpieza);

        return categorias;
    }

    public static Set<Usuario> crearUsuarios(Set<Producto> productos) {
        Usuario usuario1 = new Usuario(1L, "Juan", "Perez", "juan@mail.com", "381111111", "1234", Rol.USUARIO);
        Usuario usuario2 = new Usuario(2L, "Ana", "Gomez", "ana@mail.com", "381222222", "5678", Rol.ADMIN);

        Producto coca = buscarProductoPorNombre(productos, "Coca");
        Producto pepsi = buscarProductoPorNombre(productos, "Pepsi");
        Producto agua = buscarProductoPorNombre(productos, "Agua");
        Producto papas = buscarProductoPorNombre(productos, "Papas");
        Producto galletas = buscarProductoPorNombre(productos, "Galletas");
        Producto detergente = buscarProductoPorNombre(productos, "Detergente");

        Pedido pedido1 = new Pedido(1L, LocalDate.now(), Estado.PENDIENTE, FormaPago.EFECTIVO);
        pedido1.addDetallePedido(2, coca);
        pedido1.addDetallePedido(1, papas);

        Pedido pedido2 = new Pedido(2L, LocalDate.now(), Estado.CONFIRMADO, FormaPago.TARJETA);
        pedido2.addDetallePedido(3, pepsi);
        pedido2.addDetallePedido(2, galletas);

        Pedido pedido3 = new Pedido(3L, LocalDate.now(), Estado.TERMINADO, FormaPago.TRANSFERENCIA);
        pedido3.addDetallePedido(1, agua);
        pedido3.addDetallePedido(2, detergente);

        usuario1.agregarPedido(pedido1);
        usuario1.agregarPedido(pedido2);
        usuario2.agregarPedido(pedido3);

        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        return usuarios;
    }

    private static Producto buscarProductoPorNombre(Set<Producto> productos, String nombre) {
        for (Producto producto : productos) {
            if (producto.getNombre().equals(nombre)) {
                return producto;
            }
        }
        return null;
    }
}