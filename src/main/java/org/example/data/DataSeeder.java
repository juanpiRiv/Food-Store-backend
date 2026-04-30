package org.example.data;

import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.Categoria;
import org.example.model.Pedido;
import org.example.model.Producto;
import org.example.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class DataSeeder {

    public static Set<Producto> crearProductos() {
        Set<Producto> productos = new HashSet<>();

        productos.add(crearProducto(1L, "Coca", 1000.0, "Gaseosa cola", 10, "coca.jpg", true));
        productos.add(crearProducto(2L, "Pepsi", 1100.0, "Gaseosa cola", 8, "pepsi.jpg", true));
        productos.add(crearProducto(3L, "Agua", 800.0, "Agua mineral", 20, "agua.jpg", true));
        productos.add(crearProducto(4L, "Jugo", 950.0, "Jugo de naranja", 15, "jugo.jpg", true));
        productos.add(crearProducto(5L, "Papas", 1200.0, "Papas fritas", 12, "papas.jpg", true));
        productos.add(crearProducto(6L, "Galletas", 900.0, "Galletitas dulces", 18, "galletas.jpg", true));
        productos.add(crearProducto(7L, "Chocolate", 1500.0, "Chocolate en barra", 7, "chocolate.jpg", true));
        productos.add(crearProducto(8L, "Lavandina", 1300.0, "Lavandina 1L", 9, "lavandina.jpg", true));
        productos.add(crearProducto(9L, "Detergente", 1400.0, "Detergente líquido", 11, "detergente.jpg", true));
        productos.add(crearProducto(10L, "Esponja", 500.0, "Esponja multiuso", 25, "esponja.jpg", true));

        return productos;
    }

    public static Set<Categoria> crearCategorias(Set<Producto> productos) {
        Categoria bebidas = Categoria.builder()
                .id(1L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre("Bebidas")
                .descripcion("Bebidas frías y calientes")
                .build();

        Categoria snacks = Categoria.builder()
                .id(2L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre("Snacks")
                .descripcion("Papas, galletas y golosinas")
                .build();

        Categoria limpieza = Categoria.builder()
                .id(3L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre("Limpieza")
                .descripcion("Productos de limpieza del hogar")
                .build();

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
        Usuario usuario1 = Usuario.builder()
                .id(1L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@mail.com")
                .celular("381111111")
                .contraseña("1234")
                .rol(Rol.USUARIO)
                .build();

        Usuario usuario2 = Usuario.builder()
                .id(2L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre("Ana")
                .apellido("Gomez")
                .email("ana@mail.com")
                .celular("381222222")
                .contraseña("5678")
                .rol(Rol.ADMIN)
                .build();

        Producto coca = buscarProductoPorNombre(productos, "Coca");
        Producto pepsi = buscarProductoPorNombre(productos, "Pepsi");
        Producto agua = buscarProductoPorNombre(productos, "Agua");
        Producto papas = buscarProductoPorNombre(productos, "Papas");
        Producto galletas = buscarProductoPorNombre(productos, "Galletas");
        Producto detergente = buscarProductoPorNombre(productos, "Detergente");

        Pedido pedido1 = crearPedido(1L, Estado.PENDIENTE, FormaPago.EFECTIVO);
        pedido1.addDetallePedido(2, coca);
        pedido1.addDetallePedido(1, papas);

        Pedido pedido2 = crearPedido(2L, Estado.CONFIRMADO, FormaPago.TARJETA);
        pedido2.addDetallePedido(3, pepsi);
        pedido2.addDetallePedido(2, galletas);

        Pedido pedido3 = crearPedido(3L, Estado.TERMINADO, FormaPago.TRANSFERENCIA);
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

    private static Producto crearProducto(
            Long id,
            String nombre,
            Double precio,
            String descripcion,
            int stock,
            String imagen,
            boolean disponible
    ) {
        return Producto.builder()
                .id(id)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre(nombre)
                .precio(precio)
                .descripcion(descripcion)
                .stock(stock)
                .imagen(imagen)
                .disponible(disponible)
                .build();
    }

    private static Pedido crearPedido(Long id, Estado estado, FormaPago formaPago) {
        return Pedido.builder()
                .id(id)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .estado(estado)
                .formaPago(formaPago)
                .total(0.0)
                .build();
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