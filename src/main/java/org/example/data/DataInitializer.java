package org.example.data;

import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.Categoria;
import org.example.model.Pedido;
import org.example.model.Producto;
import org.example.model.Usuario;
import org.example.repository.CategoriaRepository;
import org.example.repository.DetallePedidoRepository;
import org.example.repository.PedidoRepository;
import org.example.repository.ProductoRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            PedidoRepository pedidoRepository,
            DetallePedidoRepository detallePedidoRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        limpiarBase();

        Categoria bebidas = Categoria.builder()
                .nombre("Bebidas")
                .descripcion("Bebidas frías y calientes")
                .build();

        Categoria snacks = Categoria.builder()
                .nombre("Snacks")
                .descripcion("Papas, galletas y golosinas")
                .build();

        Categoria limpieza = Categoria.builder()
                .nombre("Limpieza")
                .descripcion("Productos de limpieza del hogar")
                .build();

        Producto coca = crearProducto("Coca", 1000.0, "Gaseosa cola", 10, "coca.jpg", true);
        Producto pepsi = crearProducto("Pepsi", 1100.0, "Gaseosa cola", 8, "pepsi.jpg", true);
        Producto agua = crearProducto("Agua", 800.0, "Agua mineral", 20, "agua.jpg", true);
        Producto jugo = crearProducto("Jugo", 950.0, "Jugo de naranja", 15, "jugo.jpg", true);

        Producto papas = crearProducto("Papas", 1200.0, "Papas fritas", 12, "papas.jpg", true);
        Producto galletas = crearProducto("Galletas", 900.0, "Galletitas dulces", 18, "galletas.jpg", true);
        Producto chocolate = crearProducto("Chocolate", 1500.0, "Chocolate en barra", 3, "chocolate.jpg", true);

        Producto lavandina = crearProducto("Lavandina", 1300.0, "Lavandina 1L", 9, "lavandina.jpg", true);
        Producto detergente = crearProducto("Detergente", 1400.0, "Detergente líquido", 11, "detergente.jpg", true);
        Producto esponja = crearProducto("Esponja", 500.0, "Esponja multiuso", 4, "esponja.jpg", true);

        bebidas.agregarProducto(coca);
        bebidas.agregarProducto(pepsi);
        bebidas.agregarProducto(agua);
        bebidas.agregarProducto(jugo);

        snacks.agregarProducto(papas);
        snacks.agregarProducto(galletas);
        snacks.agregarProducto(chocolate);

        limpieza.agregarProducto(lavandina);
        limpieza.agregarProducto(detergente);
        limpieza.agregarProducto(esponja);

        categoriaRepository.saveAll(List.of(bebidas, snacks, limpieza));

        Usuario usuario1 = Usuario.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@mail.com")
                .celular("381111111")
                .contraseña("1234")
                .rol(Rol.USUARIO)
                .build();

        Usuario usuario2 = Usuario.builder()
                .nombre("Ana")
                .apellido("Gomez")
                .email("ana@mail.com")
                .celular("381222222")
                .contraseña("5678")
                .rol(Rol.ADMIN)
                .build();

        Pedido pedido1 = crearPedido(Estado.PENDIENTE, FormaPago.EFECTIVO);
        pedido1.addDetallePedido(2, coca);
        pedido1.addDetallePedido(1, papas);

        Pedido pedido2 = crearPedido(Estado.CONFIRMADO, FormaPago.TARJETA);
        pedido2.addDetallePedido(3, pepsi);
        pedido2.addDetallePedido(2, galletas);

        Pedido pedido3 = crearPedido(Estado.TERMINADO, FormaPago.TRANSFERENCIA);
        pedido3.addDetallePedido(1, agua);
        pedido3.addDetallePedido(2, detergente);

        usuario1.agregarPedido(pedido1);
        usuario1.agregarPedido(pedido2);
        usuario2.agregarPedido(pedido3);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        coca.setPrecio(1250.0);
        coca.setStock(20);
        productoRepository.save(coca);

        papas.setPrecio(1500.0);
        papas.setDisponible(false);
        productoRepository.save(papas);

        Usuario usuarioPorId = usuarioRepository.findById(usuario1.getId()).orElseThrow();
        System.out.println("Usuario por ID: " + usuarioPorId);

        Usuario usuarioPorMail = usuarioRepository.findByEmail("ana@mail.com").orElseThrow();
        System.out.println("Usuario por mail: " + usuarioPorMail);

        limpieza.eliminarProducto(esponja);
        categoriaRepository.save(limpieza);
        productoRepository.delete(esponja);

        imprimirResumen();

        System.out.println("TP Spring Boot ejecutado correctamente.");
    }

    private void limpiarBase() {
        detallePedidoRepository.deleteAllInBatch();
        pedidoRepository.deleteAllInBatch();
        productoRepository.deleteAllInBatch();
        categoriaRepository.deleteAllInBatch();
        usuarioRepository.deleteAllInBatch();

        detallePedidoRepository.flush();
        pedidoRepository.flush();
        productoRepository.flush();
        categoriaRepository.flush();
        usuarioRepository.flush();
    }
    private Producto crearProducto(
            String nombre,
            Double precio,
            String descripcion,
            int stock,
            String imagen,
            boolean disponible
    ) {
        return Producto.builder()
                .nombre(nombre)
                .precio(precio)
                .descripcion(descripcion)
                .stock(stock)
                .imagen(imagen)
                .disponible(disponible)
                .build();
    }

    private Pedido crearPedido(Estado estado, FormaPago formaPago) {
        return Pedido.builder()
                .fecha(LocalDate.now())
                .estado(estado)
                .formaPago(formaPago)
                .total(0.0)
                .build();
    }

    private void imprimirResumen() {
        System.out.println("=== RESUMEN FINAL SPRING BOOT ===");
        System.out.println("Usuarios persistidos: " + usuarioRepository.count());
        System.out.println("Productos persistidos: " + productoRepository.count());
        System.out.println("Categorias persistidas: " + categoriaRepository.count());
        System.out.println("Pedidos persistidos: " + pedidoRepository.count());
        System.out.println("Detalles persistidos: " + detallePedidoRepository.count());
    }
}