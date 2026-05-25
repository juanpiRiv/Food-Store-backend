package org.example.data;

import org.example.dto.categoria.CategoriaCreate;
import org.example.dto.detallePedido.DetallePedidoCreate;
import org.example.dto.producto.ProductoCreate;
import org.example.dto.producto.ProductoEdit;
import org.example.dto.usuario.UsuarioCreate;
import org.example.dto.usuario.UsuarioDto;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.Categoria;
import org.example.model.Producto;
import org.example.model.Usuario;
import org.example.repository.CategoriaRepository;
import org.example.repository.DetallePedidoRepository;
import org.example.repository.PedidoRepository;
import org.example.repository.ProductoRepository;
import org.example.repository.UsuarioRepository;
import org.example.service.CategoriaService;
import org.example.service.PedidoService;
import org.example.service.ProductoService;
import org.example.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public DataInitializer(
            CategoriaService categoriaService,
            ProductoService productoService,
            UsuarioService usuarioService,
            PedidoService pedidoService,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            PedidoRepository pedidoRepository,
            DetallePedidoRepository detallePedidoRepository
    ) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
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

        Categoria bebidas = categoriaService.crear(
                new CategoriaCreate("Bebidas", "Bebidas frías y calientes")
        );

        Categoria snacks = categoriaService.crear(
                new CategoriaCreate("Snacks", "Papas, galletas y golosinas")
        );

        Categoria limpieza = categoriaService.crear(
                new CategoriaCreate("Limpieza", "Productos de limpieza del hogar")
        );

        Producto coca = productoService.crear(
                new ProductoCreate("Coca", 1000.0, "Gaseosa cola", 10, "coca.jpg", true, bebidas.getId())
        );

        Producto pepsi = productoService.crear(
                new ProductoCreate("Pepsi", 1100.0, "Gaseosa cola", 8, "pepsi.jpg", true, bebidas.getId())
        );

        Producto agua = productoService.crear(
                new ProductoCreate("Agua", 800.0, "Agua mineral", 20, "agua.jpg", true, bebidas.getId())
        );

        Producto jugo = productoService.crear(
                new ProductoCreate("Jugo", 950.0, "Jugo de naranja", 15, "jugo.jpg", true, bebidas.getId())
        );

        Producto papas = productoService.crear(
                new ProductoCreate("Papas", 1200.0, "Papas fritas", 12, "papas.jpg", true, snacks.getId())
        );

        Producto galletas = productoService.crear(
                new ProductoCreate("Galletas", 900.0, "Galletitas dulces", 18, "galletas.jpg", true, snacks.getId())
        );

        Producto chocolate = productoService.crear(
                new ProductoCreate("Chocolate", 1500.0, "Chocolate en barra", 3, "chocolate.jpg", true, snacks.getId())
        );

        Producto lavandina = productoService.crear(
                new ProductoCreate("Lavandina", 1300.0, "Lavandina 1L", 9, "lavandina.jpg", true, limpieza.getId())
        );

        Producto detergente = productoService.crear(
                new ProductoCreate("Detergente", 1400.0, "Detergente líquido", 11, "detergente.jpg", true, limpieza.getId())
        );

        Producto esponja = productoService.crear(
                new ProductoCreate("Esponja", 500.0, "Esponja multiuso", 4, "esponja.jpg", true, limpieza.getId())
        );

        Usuario usuario1 = usuarioService.crear(
                new UsuarioCreate(
                        "Juan",
                        "Perez",
                        "juan@mail.com",
                        "381111111",
                        "1234",
                        Rol.USUARIO
                )
        );

        Usuario usuario2 = usuarioService.crear(
                new UsuarioCreate(
                        "Ana",
                        "Gomez",
                        "ana@mail.com",
                        "381222222",
                        "5678",
                        Rol.ADMIN
                )
        );

        pedidoService.crear(
                usuario1.getId(),
                Estado.PENDIENTE,
                FormaPago.EFECTIVO,
                List.of(
                        new DetallePedidoCreate(2, coca.getId()),
                        new DetallePedidoCreate(1, papas.getId())
                )
        );

        pedidoService.crear(
                usuario1.getId(),
                Estado.CONFIRMADO,
                FormaPago.TARJETA,
                List.of(
                        new DetallePedidoCreate(3, pepsi.getId()),
                        new DetallePedidoCreate(2, galletas.getId())
                )
        );

        pedidoService.crear(
                usuario2.getId(),
                Estado.TERMINADO,
                FormaPago.TRANSFERENCIA,
                List.of(
                        new DetallePedidoCreate(1, agua.getId()),
                        new DetallePedidoCreate(2, detergente.getId())
                )
        );

        productoService.editar(
                coca.getId(),
                new ProductoEdit(
                        "Coca",
                        1250.0,
                        "Gaseosa cola",
                        20,
                        "coca.jpg",
                        true,
                        bebidas.getId()
                )
        );

        productoService.editar(
                papas.getId(),
                new ProductoEdit(
                        "Papas",
                        1500.0,
                        "Papas fritas",
                        12,
                        "papas.jpg",
                        false,
                        snacks.getId()
                )
        );

        UsuarioDto usuarioPorId = usuarioService.buscarPorId(usuario1.getId());
        System.out.println("Usuario por ID: " + usuarioPorId.getNombre() + " - " + usuarioPorId.getEmail());

        UsuarioDto usuarioPorMail = usuarioService.buscarPorEmail("ana@mail.com");
        System.out.println("Usuario por mail: " + usuarioPorMail.getNombre() + " - " + usuarioPorMail.getEmail());

        productoService.eliminar(esponja.getId());

        imprimirResumen();

        System.out.println("TP Spring Boot ejecutado correctamente usando DTOs y Services.");
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

    private void imprimirResumen() {
        System.out.println("=== RESUMEN FINAL SPRING BOOT ===");
        System.out.println("Usuarios persistidos: " + usuarioRepository.count());
        System.out.println("Productos persistidos: " + productoRepository.count());
        System.out.println("Categorias persistidas: " + categoriaRepository.count());
        System.out.println("Pedidos persistidos: " + pedidoRepository.count());
        System.out.println("Detalles persistidos: " + detallePedidoRepository.count());
    }
}