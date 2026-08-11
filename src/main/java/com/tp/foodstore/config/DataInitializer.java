package com.tp.foodstore.config;

import com.tp.foodstore.dto.categoria.CategoriaCreate;
import com.tp.foodstore.dto.detallePedido.DetallePedidoCreate;
import com.tp.foodstore.dto.pedido.PedidoEdit;
import com.tp.foodstore.dto.producto.ProductoCreate;
import com.tp.foodstore.dto.usuario.UsuarioCreate;
import com.tp.foodstore.entity.Categoria;
import com.tp.foodstore.entity.Pedido;
import com.tp.foodstore.entity.Producto;
import com.tp.foodstore.entity.Usuario;
import com.tp.foodstore.entity.enums.Estado;
import com.tp.foodstore.entity.enums.FormaPago;
import com.tp.foodstore.entity.enums.Rol;
import com.tp.foodstore.mapper.CategoriaMapper;
import com.tp.foodstore.mapper.PedidoMapper;
import com.tp.foodstore.mapper.ProductoMapper;
import com.tp.foodstore.mapper.UsuarioMapper;
import com.tp.foodstore.repository.CategoriaRepository;
import com.tp.foodstore.repository.PedidoRepository;
import com.tp.foodstore.repository.ProductoRepository;
import com.tp.foodstore.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Persiste datos de ejemplo en la base H2 a partir de los DTOs de creación,
 * para mostrar el flujo DTO -> entidad -> base al iniciar la aplicación.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaMapper categoriaMapper;
    private final ProductoMapper productoMapper;
    private final UsuarioMapper usuarioMapper;
    private final PedidoMapper pedidoMapper;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public DataInitializer(CategoriaMapper categoriaMapper,
                           ProductoMapper productoMapper,
                           UsuarioMapper usuarioMapper,
                           PedidoMapper pedidoMapper,
                           CategoriaRepository categoriaRepository,
                           ProductoRepository productoRepository,
                           UsuarioRepository usuarioRepository,
                           PedidoRepository pedidoRepository) {
        this.categoriaMapper = categoriaMapper;
        this.productoMapper = productoMapper;
        this.usuarioMapper = usuarioMapper;
        this.pedidoMapper = pedidoMapper;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {
        List<Usuario> usuarios = new ArrayList<>();
        for (Usuario usuario : crearUsuarios()) {
            usuarios.add(usuarioRepository.save(usuario));
        }

        List<Categoria> categorias = new ArrayList<>();
        for (Categoria categoria : crearCategorias()) {
            categorias.add(categoriaRepository.save(categoria));
        }

        List<Producto> productos = new ArrayList<>();
        for (Producto producto : crearProductos(categorias)) {
            productos.add(productoRepository.save(producto));
        }

        List<Pedido> pedidos = new ArrayList<>();
        for (Pedido pedido : crearPedidos(usuarios, productos)) {
            pedidos.add(pedidoRepository.save(pedido));
        }

        imprimirResumen(usuarios, categorias, productos, pedidos);
    }

    private List<Usuario> crearUsuarios() {
        UsuarioCreate ana = UsuarioCreate.builder()
                .nombre("Ana")
                .apellido("García")
                .mail("anagarcia@mail.com")
                .celular("11-5555-0101")
                .contrasena("admin123")
                .rol(Rol.ADMIN)
                .build();

        UsuarioCreate carlos = UsuarioCreate.builder()
                .nombre("Carlos")
                .apellido("Pérez")
                .mail("carlosperez@mail.com")
                .celular("11-5555-0202")
                .contrasena("clave123")
                .rol(Rol.USUARIO)
                .build();

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioMapper.toEntity(ana));
        usuarios.add(usuarioMapper.toEntity(carlos));
        return usuarios;
    }

    private List<Categoria> crearCategorias() {
        CategoriaCreate hamburguesas = CategoriaCreate.builder()
                .nombre("Hamburguesas")
                .descripcion("Clásicas y especiales, con pan artesanal")
                .build();

        CategoriaCreate pizzas = CategoriaCreate.builder()
                .nombre("Pizzas")
                .descripcion("A la piedra, con ingredientes frescos")
                .build();

        CategoriaCreate bebidas = CategoriaCreate.builder()
                .nombre("Bebidas")
                .descripcion("Gaseosas, aguas y jugos bien fríos")
                .build();

        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoriaMapper.toEntity(hamburguesas));
        categorias.add(categoriaMapper.toEntity(pizzas));
        categorias.add(categoriaMapper.toEntity(bebidas));
        return categorias;
    }

    private List<Producto> crearProductos(List<Categoria> categorias) {
        ProductoCreate clasica = ProductoCreate.builder()
                .nombre("Hamburguesa Clásica")
                .precio(8500.0)
                .descripcion("Medallón de carne, lechuga, tomate y mayonesa casera")
                .stock(20)
                .imagen("hamburguesa-clasica.jpg")
                .disponible(true)
                .build();

        ProductoCreate conCheddar = ProductoCreate.builder()
                .nombre("Hamburguesa con Cheddar")
                .precio(9500.0)
                .descripcion("Medallón doble, cheddar extra y panceta")
                .stock(15)
                .imagen("hamburguesa-cheddar.jpg")
                .disponible(true)
                .build();

        ProductoCreate veggie = ProductoCreate.builder()
                .nombre("Hamburguesa Veggie")
                .precio(8200.0)
                .descripcion("Medallón de garbanzos, rúcula y queso vegano")
                .stock(10)
                .imagen("hamburguesa-veggie.jpg")
                .disponible(true)
                .build();

        ProductoCreate margarita = ProductoCreate.builder()
                .nombre("Pizza Margarita")
                .precio(9800.0)
                .descripcion("Mozzarella, tomate y albahaca fresca")
                .stock(12)
                .imagen("pizza-margarita.jpg")
                .disponible(true)
                .build();

        ProductoCreate calabresa = ProductoCreate.builder()
                .nombre("Pizza Calabresa")
                .precio(10500.0)
                .descripcion("Mozzarella, longaniza y aceitunas")
                .stock(12)
                .imagen("pizza-calabresa.jpg")
                .disponible(true)
                .build();

        ProductoCreate cuatroQuesos = ProductoCreate.builder()
                .nombre("Pizza Cuatro Quesos")
                .precio(11200.0)
                .descripcion("Mozzarella, roquefort, parmesano y provolone")
                .stock(8)
                .imagen("pizza-cuatro-quesos.jpg")
                .disponible(true)
                .build();

        ProductoCreate cola = ProductoCreate.builder()
                .nombre("Coca-Cola 500ml")
                .precio(2500.0)
                .descripcion("Gaseosa cola bien fría")
                .stock(50)
                .imagen("coca-cola-500.jpg")
                .disponible(true)
                .build();

        ProductoCreate agua = ProductoCreate.builder()
                .nombre("Agua Mineral 500ml")
                .precio(1800.0)
                .descripcion("Agua sin gas")
                .stock(60)
                .imagen("agua-500.jpg")
                .disponible(true)
                .build();

        ProductoCreate jugo = ProductoCreate.builder()
                .nombre("Jugo de Naranja Natural")
                .precio(3200.0)
                .descripcion("Exprimido en el momento")
                .stock(25)
                .imagen("jugo-naranja.jpg")
                .disponible(true)
                .build();

        ProductoCreate cerveza = ProductoCreate.builder()
                .nombre("Cerveza Artesanal 473ml")
                .precio(4200.0)
                .descripcion("IPA rubia de producción local")
                .stock(30)
                .imagen("cerveza-473.jpg")
                .disponible(true)
                .build();

        List<Producto> productos = new ArrayList<>();
        productos.add(productoMapper.toEntity(clasica, categorias.get(0)));
        productos.add(productoMapper.toEntity(conCheddar, categorias.get(0)));
        productos.add(productoMapper.toEntity(veggie, categorias.get(0)));
        productos.add(productoMapper.toEntity(margarita, categorias.get(1)));
        productos.add(productoMapper.toEntity(calabresa, categorias.get(1)));
        productos.add(productoMapper.toEntity(cuatroQuesos, categorias.get(1)));
        productos.add(productoMapper.toEntity(cola, categorias.get(2)));
        productos.add(productoMapper.toEntity(agua, categorias.get(2)));
        productos.add(productoMapper.toEntity(jugo, categorias.get(2)));
        productos.add(productoMapper.toEntity(cerveza, categorias.get(2)));
        return productos;
    }

    private List<Pedido> crearPedidos(List<Usuario> usuarios, List<Producto> productos) {
        List<Pedido> pedidos = new ArrayList<>();

        PedidoEdit pedido1 = PedidoEdit.builder()
                .fecha(LocalDateTime.now().minusDays(1))
                .estado(Estado.CONFIRMADO)
                .formaPago(FormaPago.TARJETA)
                .detalles(List.of(
                        detalle(2, 17000.0),
                        detalle(2, 5000.0)))
                .build();
        pedidos.add(crearPedido(pedido1, usuarios.get(0),
                List.of(productos.get(0), productos.get(6))));

        PedidoEdit pedido2 = PedidoEdit.builder()
                .fecha(LocalDateTime.now().minusHours(2))
                .estado(Estado.PENDIENTE)
                .formaPago(FormaPago.EFECTIVO)
                .detalles(List.of(
                        detalle(1, 9800.0),
                        detalle(1, 10500.0),
                        detalle(2, 3600.0)))
                .build();
        pedidos.add(crearPedido(pedido2, usuarios.get(1),
                List.of(productos.get(3), productos.get(4), productos.get(7))));

        PedidoEdit pedido3 = PedidoEdit.builder()
                .fecha(LocalDateTime.now().minusMinutes(30))
                .estado(Estado.CANCELADO)
                .formaPago(FormaPago.TRANSFERENCIA)
                .detalles(List.of(
                        detalle(1, 8200.0),
                        detalle(1, 3200.0),
                        detalle(1, 4200.0)))
                .build();
        pedidos.add(crearPedido(pedido3, usuarios.get(1),
                List.of(productos.get(2), productos.get(8), productos.get(9))));

        return pedidos;
    }

    private Pedido crearPedido(PedidoEdit edit, Usuario usuario, List<Producto> productos) {
        Pedido pedido = pedidoMapper.toEntity(edit, usuario, productos);
        pedido.calcularTotal();
        return pedido;
    }

    private DetallePedidoCreate detalle(int cantidad, Double subtotal) {
        return DetallePedidoCreate.builder()
                .cantidad(cantidad)
                .subtotal(subtotal)
                .build();
    }

    private void imprimirResumen(List<Usuario> usuarios,
                                 List<Categoria> categorias,
                                 List<Producto> productos,
                                 List<Pedido> pedidos) {
        System.out.println("===== DATOS DE EJEMPLO INSTANCIADOS =====");

        System.out.println("\nUsuarios (" + usuarios.size() + "):");
        for (Usuario u : usuarios) {
            System.out.println("  - id " + u.getId() + ": " + u.getNombre() + " " + u.getApellido()
                    + " (" + u.getMail() + ") - rol " + u.getRol());
        }

        System.out.println("\nCategorías (" + categorias.size() + "):");
        for (Categoria c : categorias) {
            System.out.println("  - id " + c.getId() + ": " + c.getNombre());
        }

        System.out.println("\nProductos (" + productos.size() + "):");
        for (Producto p : productos) {
            System.out.println("  - id " + p.getId() + ": " + p.getNombre()
                    + " ($" + p.getPrecio() + ") - stock " + p.getStock()
                    + " - categoría " + p.getCategoria().getNombre());
        }

        System.out.println("\nPedidos (" + pedidos.size() + "):");
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            System.out.println("  - Pedido " + (i + 1) + " (id " + p.getId()
                    + "): estado " + p.getEstado()
                    + ", forma de pago " + p.getFormaPago()
                    + ", total $" + p.getTotal()
                    + " (" + p.getDetalles().size() + " detalles)");
        }

        int totalDetalles = pedidos.stream()
                .mapToInt(p -> p.getDetalles().size())
                .sum();
        System.out.println("\nGuardados en la base: " + usuarioRepository.count() + " usuarios, "
                + categoriaRepository.count() + " categorias, "
                + productoRepository.count() + " productos, "
                + pedidoRepository.count() + " pedidos ("
                + totalDetalles + " detalles en total)");
        System.out.println("\n===== FIN DEL RESUMEN =====");
    }
}
