package com.tp.jpa;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.DetallePedido;
import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.Producto;
import com.tp.jpa.model.Usuario;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.model.enums.Rol;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.PedidoRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.repository.UsuarioRepository;
import com.tp.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    static {
        // hibernate llena la consola de logs; los apago para que la demo se vea limpia
        System.setProperty("org.jboss.logging.provider", "jdk");
        LogManager.getLogManager().reset();
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.OFF);
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Logger.getLogger("org.jboss").setLevel(Level.OFF);
        Logger.getLogger("jakarta.persistence").setLevel(Level.OFF);
    }

    private static final Scanner sc = new Scanner(System.in);

    private static CategoriaRepository categoriaRepository;
    private static ProductoRepository productoRepository;
    private static UsuarioRepository usuarioRepository;
    private static PedidoRepository pedidoRepository;

    public static void main(String[] args) {
        inicializarRepositorios();

        int opcion;

        try {
            do {
                mostrarMenuPrincipal();
                opcion = leerEntero("Seleccione una opcion: ");

                switch (opcion) {
                    case 1 -> menuCategorias();
                    case 2 -> menuProductos();
                    case 3 -> menuUsuarios();
                    case 4 -> menuPedidos();
                    case 5 -> menuReportes();
                    case 0 -> System.out.println("Programa finalizado.");
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcion != 0);
        } finally {
            JpaUtil.cerrar();
            sc.close();
        }
    }

    private static void inicializarRepositorios() {
        categoriaRepository = new CategoriaRepository();
        productoRepository = new ProductoRepository();
        usuarioRepository = new UsuarioRepository();
        pedidoRepository = new PedidoRepository();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== FOOD STORE - MENU PRINCIPAL =====");
        System.out.println("1. Gestionar Categorias");
        System.out.println("2. Gestionar Productos");
        System.out.println("3. Gestionar Usuarios");
        System.out.println("4. Gestionar Pedidos");
        System.out.println("5. Reportes");
        System.out.println("0. Salir");
    }

    // ===== CATEGORIAS =====

    private static void menuCategorias() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE CATEGORIAS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja logica");
            System.out.println("4. Listado");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> altaCategoria();
                case 2 -> modificarCategoria();
                case 3 -> bajaCategoria();
                case 4 -> listarCategorias();
                case 0 -> System.out.println("Volviendo al menu principal.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void altaCategoria() {
        System.out.println("\n--- Alta de categoria ---");

        String nombre = leerTexto("Nombre: ");

        if (nombre.isBlank()) {
            System.out.println("Error: el nombre no puede estar vacio.");
            return;
        }

        String descripcion = leerTexto("Descripcion: ");

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoria.setEliminado(false);

        Categoria guardada = categoriaRepository.guardar(categoria);
        System.out.println("Categoria creada. ID: " + guardada.getId());
    }

    private static void modificarCategoria() {
        System.out.println("\n--- Modificacion de categoria ---");

        listarCategorias();
        Long id = leerLong("Ingrese ID de la categoria: ");

        Optional<Categoria> opt = categoriaRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: no existe una categoria activa con ese ID.");
            return;
        }

        Categoria categoria = opt.get();
        System.out.println("Nombre actual: " + categoria.getNombre());
        System.out.println("Descripcion actual: " + categoria.getDescripcion());

        String nuevoNombre = leerTexto("Nuevo nombre (Enter para conservar): ");
        String nuevaDescripcion = leerTexto("Nueva descripcion (Enter para conservar): ");

        if (!nuevoNombre.isBlank()) categoria.setNombre(nuevoNombre);
        if (!nuevaDescripcion.isBlank()) categoria.setDescripcion(nuevaDescripcion);

        categoriaRepository.guardar(categoria);
        System.out.println("Categoria modificada correctamente.");
    }

    private static void bajaCategoria() {
        System.out.println("\n--- Baja logica de categoria ---");

        listarCategorias();
        Long id = leerLong("Ingrese ID de la categoria: ");

        Optional<Categoria> opt = categoriaRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: la categoria no existe o ya esta dada de baja.");
            return;
        }

        boolean eliminada = categoriaRepository.eliminarLogico(id);

        if (eliminada) {
            System.out.println("Categoria dada de baja: " + opt.get().getNombre());
        } else {
            System.out.println("No se pudo dar de baja la categoria.");
        }
    }

    private static void listarCategorias() {
        System.out.println("\n--- Categorias activas ---");

        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas.");
            return;
        }

        for (Categoria c : categorias) {
            System.out.printf("ID: %d | Nombre: %-20s | Descripcion: %s%n",
                    c.getId(), c.getNombre(), c.getDescripcion());
        }
    }

    // ===== PRODUCTOS =====

    private static void menuProductos() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE PRODUCTOS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja logica");
            System.out.println("4. Listado");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> altaProducto();
                case 2 -> modificarProducto();
                case 3 -> bajaProducto();
                case 4 -> listarProductos();
                case 0 -> System.out.println("Volviendo al menu principal.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void altaProducto() {
        System.out.println("\n--- Alta de producto ---");

        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas. Cree una categoria primero.");
            return;
        }

        listarCategorias();
        Long categoriaId = leerLong("Seleccione ID de categoria: ");

        Optional<Categoria> categoriaOpt = categoriaRepository.buscarPorId(categoriaId);

        if (categoriaOpt.isEmpty() || categoriaOpt.get().isEliminado()) {
            System.out.println("Error: categoria inexistente o dada de baja.");
            return;
        }

        String nombre = leerTexto("Nombre: ");

        if (nombre.isBlank()) {
            System.out.println("Error: el nombre no puede estar vacio.");
            return;
        }

        String descripcion = leerTexto("Descripcion: ");
        double precio = leerDouble("Precio: ");

        if (precio <= 0) {
            System.out.println("Error: el precio debe ser mayor a 0.");
            return;
        }

        int stock = leerEntero("Stock: ");

        if (stock < 0) {
            System.out.println("Error: el stock no puede ser negativo.");
            return;
        }

        String imagenInput = leerTexto("Imagen (URL o nombre, Enter para omitir): ");

        String disponibleInput = leerTexto("Disponible S/N (Enter = S): ");
        boolean disponible = !disponibleInput.equalsIgnoreCase("N");

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setImagen(imagenInput.isBlank() ? null : imagenInput);
        producto.setDisponible(disponible);
        producto.setCategoria(categoriaOpt.get());
        producto.setEliminado(false);

        Producto guardado = productoRepository.guardar(producto);
        System.out.println("Producto creado. ID: " + guardado.getId()
                + " | Categoria: " + categoriaOpt.get().getNombre());
    }

    private static void modificarProducto() {
        System.out.println("\n--- Modificacion de producto ---");

        listarProductos();
        Long id = leerLong("Ingrese ID del producto: ");

        Optional<Producto> opt = productoRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: no existe un producto activo con ese ID.");
            return;
        }

        Producto producto = opt.get();
        System.out.printf("Nombre: %s | Precio: %.2f | Stock: %d | Disponible: %s%n",
                producto.getNombre(), producto.getPrecio(), producto.getStock(),
                producto.isDisponible() ? "Si" : "No");

        String nuevoNombre = leerTexto("Nuevo nombre (Enter para conservar): ");
        String nuevoPrecioTxt = leerTexto("Nuevo precio (Enter para conservar): ");
        String nuevoStockTxt = leerTexto("Nuevo stock (Enter para conservar): ");
        String nuevoDisponibleTxt = leerTexto("Disponible S/N (Enter para conservar): ");

        if (!nuevoNombre.isBlank()) producto.setNombre(nuevoNombre);

        if (!nuevoPrecioTxt.isBlank()) {
            try {
                double p = Double.parseDouble(nuevoPrecioTxt);
                if (p <= 0) { System.out.println("Error: precio invalido."); return; }
                producto.setPrecio(p);
            } catch (NumberFormatException e) {
                System.out.println("Error: formato de precio invalido.");
                return;
            }
        }

        if (!nuevoStockTxt.isBlank()) {
            try {
                int s = Integer.parseInt(nuevoStockTxt);
                if (s < 0) { System.out.println("Error: stock negativo."); return; }
                producto.setStock(s);
            } catch (NumberFormatException e) {
                System.out.println("Error: formato de stock invalido.");
                return;
            }
        }

        if (!nuevoDisponibleTxt.isBlank()) {
            producto.setDisponible(!nuevoDisponibleTxt.equalsIgnoreCase("N"));
        }

        productoRepository.guardar(producto);
        System.out.println("Producto modificado correctamente.");
    }

    private static void bajaProducto() {
        System.out.println("\n--- Baja logica de producto ---");

        listarProductos();
        Long id = leerLong("Ingrese ID del producto: ");

        Optional<Producto> opt = productoRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: el producto no existe o ya esta dado de baja.");
            return;
        }

        boolean eliminado = productoRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Producto dado de baja: " + opt.get().getNombre());
        } else {
            System.out.println("No se pudo dar de baja el producto.");
        }
    }

    private static void listarProductos() {
        System.out.println("\n--- Productos activos ---");

        List<Producto> productos = productoRepository.listarActivos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos.");
            return;
        }

        for (Producto p : productos) {
            String cat = p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoria";
            System.out.printf("ID: %d | %-20s | Precio: %8.2f | Stock: %3d | %s | Cat: %s%n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                    p.isDisponible() ? "Disponible" : "No disp.", cat);
        }
    }

    // ===== USUARIOS =====

    private static void menuUsuarios() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE USUARIOS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja logica");
            System.out.println("4. Listado");
            System.out.println("5. Buscar por mail");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> altaUsuario();
                case 2 -> modificarUsuario();
                case 3 -> bajaUsuario();
                case 4 -> listarUsuarios();
                case 5 -> buscarPorMail();
                case 0 -> System.out.println("Volviendo al menu principal.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void altaUsuario() {
        System.out.println("\n--- Alta de usuario ---");

        String nombre = leerTexto("Nombre: ");
        if (nombre.isBlank()) { System.out.println("Error: nombre requerido."); return; }

        String apellido = leerTexto("Apellido: ");
        if (apellido.isBlank()) { System.out.println("Error: apellido requerido."); return; }

        String mail = leerTexto("Mail: ");
        if (mail.isBlank()) { System.out.println("Error: mail requerido."); return; }

        // verifico que el mail no este en uso
        Optional<Usuario> existente = usuarioRepository.buscarPorMail(mail);
        if (existente.isPresent()) {
            System.out.println("Error: ya existe un usuario con ese mail.");
            return;
        }

        String celular = leerTexto("Celular: ");
        String contrasena = leerTexto("Contrasena: ");
        if (contrasena.isBlank()) { System.out.println("Error: contrasena requerida."); return; }

        System.out.println("Rol: 1=ADMIN, 2=USUARIO");
        int rolNum = leerEntero("Seleccione rol: ");
        Rol rol = (rolNum == 1) ? Rol.ADMIN : Rol.USUARIO;

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasena(contrasena);
        usuario.setRol(rol);
        usuario.setEliminado(false);

        Usuario guardado = usuarioRepository.guardar(usuario);
        System.out.println("Usuario creado. ID: " + guardado.getId() + " | Rol: " + guardado.getRol());
    }

    private static void modificarUsuario() {
        System.out.println("\n--- Modificacion de usuario ---");

        listarUsuarios();
        Long id = leerLong("Ingrese ID del usuario: ");

        Optional<Usuario> opt = usuarioRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: no existe un usuario activo con ese ID.");
            return;
        }

        Usuario usuario = opt.get();
        System.out.printf("Nombre: %s %s | Mail: %s | Rol: %s%n",
                usuario.getNombre(), usuario.getApellido(), usuario.getMail(), usuario.getRol());

        String nuevoNombre = leerTexto("Nuevo nombre (Enter para conservar): ");
        String nuevoApellido = leerTexto("Nuevo apellido (Enter para conservar): ");
        String nuevoMail = leerTexto("Nuevo mail (Enter para conservar): ");
        String nuevoCelular = leerTexto("Nuevo celular (Enter para conservar): ");

        if (!nuevoNombre.isBlank()) usuario.setNombre(nuevoNombre);
        if (!nuevoApellido.isBlank()) usuario.setApellido(nuevoApellido);

        if (!nuevoMail.isBlank() && !nuevoMail.equals(usuario.getMail())) {
            Optional<Usuario> existente = usuarioRepository.buscarPorMail(nuevoMail);
            if (existente.isPresent()) {
                System.out.println("Error: ese mail ya esta en uso por otro usuario.");
                return;
            }
            usuario.setMail(nuevoMail);
        }

        if (!nuevoCelular.isBlank()) usuario.setCelular(nuevoCelular);

        usuarioRepository.guardar(usuario);
        System.out.println("Usuario modificado correctamente.");
    }

    private static void bajaUsuario() {
        System.out.println("\n--- Baja logica de usuario ---");

        listarUsuarios();
        Long id = leerLong("Ingrese ID del usuario: ");

        Optional<Usuario> opt = usuarioRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: el usuario no existe o ya esta dado de baja.");
            return;
        }

        boolean eliminado = usuarioRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Usuario dado de baja: " + opt.get().getMail());
        } else {
            System.out.println("No se pudo dar de baja el usuario.");
        }
    }

    private static void listarUsuarios() {
        System.out.println("\n--- Usuarios activos ---");

        List<Usuario> usuarios = usuarioRepository.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios activos.");
            return;
        }

        for (Usuario u : usuarios) {
            System.out.printf("ID: %d | %-15s %-15s | Mail: %-30s | Rol: %s%n",
                    u.getId(), u.getNombre(), u.getApellido(), u.getMail(), u.getRol());
        }
    }

    private static void buscarPorMail() {
        System.out.println("\n--- Buscar usuario por mail ---");

        String mail = leerTexto("Mail: ");
        Optional<Usuario> opt = usuarioRepository.buscarPorMail(mail);

        if (opt.isEmpty()) {
            System.out.println("No se encontro un usuario activo con ese mail.");
            return;
        }

        Usuario u = opt.get();
        System.out.printf("ID: %d | Nombre: %s %s | Celular: %s | Rol: %s%n",
                u.getId(), u.getNombre(), u.getApellido(), u.getCelular(), u.getRol());
    }

    // ===== PEDIDOS =====

    private static void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n===== GESTION DE PEDIDOS =====");
            System.out.println("1. Alta de pedido");
            System.out.println("2. Cambiar estado");
            System.out.println("3. Baja logica");
            System.out.println("4. Listado");
            System.out.println("5. Pedidos por usuario");
            System.out.println("6. Pedidos por estado");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> altaPedido();
                case 2 -> cambiarEstadoPedido();
                case 3 -> bajaPedido();
                case 4 -> listarPedidos();
                case 5 -> pedidosPorUsuario();
                case 6 -> pedidosPorEstado();
                case 0 -> System.out.println("Volviendo al menu principal.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void altaPedido() {
        System.out.println("\n--- Alta de pedido ---");

        List<Usuario> usuarios = usuarioRepository.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios activos. Cree un usuario primero.");
            return;
        }

        listarUsuarios();
        Long usuarioId = leerLong("Seleccione ID del usuario: ");

        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);

        if (usuarioOpt.isEmpty() || usuarioOpt.get().isEliminado()) {
            System.out.println("Error: usuario no encontrado.");
            return;
        }

        System.out.println("Forma de pago: 1=TARJETA  2=TRANSFERENCIA  3=EFECTIVO");
        int pagoNum = leerEntero("Seleccione forma de pago: ");
        FormaPago formaPago = switch (pagoNum) {
            case 1 -> FormaPago.TARJETA;
            case 2 -> FormaPago.TRANSFERENCIA;
            default -> FormaPago.EFECTIVO;
        };

        // acumulo los items antes de abrir la transaccion definitiva
        List<long[]> items = new ArrayList<>();

        System.out.println("Agregue productos al pedido (0 para terminar):");

        while (true) {
            listarProductos();
            Long productoId = leerLong("ID del producto (0 para terminar): ");

            if (productoId == 0) break;

            Optional<Producto> productoOpt = productoRepository.buscarPorId(productoId);

            if (productoOpt.isEmpty() || productoOpt.get().isEliminado()) {
                System.out.println("Producto no encontrado o dado de baja.");
                continue;
            }

            Producto prod = productoOpt.get();

            if (!prod.isDisponible()) {
                System.out.println("Producto no disponible: " + prod.getNombre());
                continue;
            }

            int cantidad = leerEntero("Cantidad: ");

            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor a 0.");
                continue;
            }

            if (!prod.tieneStock(cantidad)) {
                System.out.printf("Stock insuficiente. Disponible: %d%n", prod.getStock());
                continue;
            }

            items.add(new long[]{productoId, cantidad});
            System.out.println("Item agregado: " + prod.getNombre() + " x" + cantidad);
        }

        if (items.isEmpty()) {
            System.out.println("Pedido cancelado: no se agregaron productos.");
            return;
        }

        // todo en una sola transaccion atomica
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // busco el usuario gestionado dentro de este EntityManager
            Usuario usuario = em.find(Usuario.class, usuarioId);

            Pedido pedido = new Pedido();
            pedido.setFecha(LocalDateTime.now());
            pedido.setEstado(Estado.PENDIENTE);
            pedido.setFormaPago(formaPago);
            pedido.setEliminado(false);

            for (long[] item : items) {
                Producto prod = em.find(Producto.class, item[0]);
                int cant = (int) item[1];

                // revalido stock dentro de la transaccion por si cambio entre medicion y ahora
                if (!prod.tieneStock(cant)) {
                    tx.rollback();
                    System.out.printf("Rollback: stock insuficiente para '%s' (disponible: %d).%n",
                            prod.getNombre(), prod.getStock());
                    return;
                }

                pedido.addDetallePedido(cant, prod);
                prod.setStock(prod.getStock() - cant);
            }

            pedido.calcularTotal();
            em.persist(pedido);

            // flush para que el pedido tenga ID antes de asignarlo al usuario
            em.flush();
            usuario.getPedidos().add(pedido);

            tx.commit();

            System.out.println("\nPedido creado correctamente.");
            System.out.println("ID: " + pedido.getId());
            System.out.printf("Total: $%.2f | Forma de pago: %s%n", pedido.getTotal(), pedido.getFormaPago());
            System.out.println("Estado: " + pedido.getEstado());

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Error al crear el pedido: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    private static void cambiarEstadoPedido() {
        System.out.println("\n--- Cambiar estado de pedido ---");

        listarPedidos();
        Long id = leerLong("Ingrese ID del pedido: ");

        Optional<Pedido> opt = pedidoRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: pedido no encontrado o dado de baja.");
            return;
        }

        Pedido pedido = opt.get();
        System.out.println("Estado actual: " + pedido.getEstado());
        System.out.println("1=PENDIENTE  2=CONFIRMADO  3=TERMINADO  4=CANCELADO");
        int estadoNum = leerEntero("Nuevo estado: ");

        Estado nuevoEstado = switch (estadoNum) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };

        if (nuevoEstado == null) {
            System.out.println("Opcion invalida.");
            return;
        }

        pedido.setEstado(nuevoEstado);
        pedidoRepository.guardar(pedido);
        System.out.println("Estado actualizado a: " + nuevoEstado);
    }

    private static void bajaPedido() {
        System.out.println("\n--- Baja logica de pedido ---");

        listarPedidos();
        Long id = leerLong("Ingrese ID del pedido: ");

        Optional<Pedido> opt = pedidoRepository.buscarPorId(id);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: pedido no encontrado o ya dado de baja.");
            return;
        }

        boolean eliminado = pedidoRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Pedido dado de baja. ID: " + id);
        } else {
            System.out.println("No se pudo dar de baja el pedido.");
        }
    }

    private static void listarPedidos() {
        System.out.println("\n--- Pedidos activos ---");

        List<Pedido> pedidos = pedidoRepository.listarActivos();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos activos.");
            return;
        }

        for (Pedido p : pedidos) {
            System.out.printf("ID: %d | Fecha: %s | Estado: %-12s | Total: %8.2f | Pago: %s%n",
                    p.getId(), p.getFecha().toLocalDate(), p.getEstado(),
                    p.getTotal(), p.getFormaPago());
        }
    }

    private static void pedidosPorUsuario() {
        System.out.println("\n--- Pedidos por usuario ---");

        listarUsuarios();
        Long usuarioId = leerLong("Seleccione ID del usuario: ");

        Optional<Usuario> opt = usuarioRepository.buscarPorId(usuarioId);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: usuario no encontrado.");
            return;
        }

        List<Pedido> pedidos = pedidoRepository.buscarPorUsuario(usuarioId);

        System.out.printf("Pedidos de %s %s:%n",
                opt.get().getNombre(), opt.get().getApellido());

        if (pedidos.isEmpty()) {
            System.out.println("No tiene pedidos activos.");
            return;
        }

        for (Pedido p : pedidos) {
            System.out.printf("ID: %d | Fecha: %s | Estado: %-12s | Total: $%.2f%n",
                    p.getId(), p.getFecha().toLocalDate(), p.getEstado(), p.getTotal());
        }
    }

    private static void pedidosPorEstado() {
        System.out.println("\n--- Pedidos por estado ---");
        System.out.println("1=PENDIENTE  2=CONFIRMADO  3=TERMINADO  4=CANCELADO");
        int estadoNum = leerEntero("Seleccione estado: ");

        Estado estado = switch (estadoNum) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };

        if (estado == null) {
            System.out.println("Opcion invalida.");
            return;
        }

        List<Pedido> pedidos = pedidoRepository.buscarPorEstado(estado);

        System.out.println("Pedidos con estado " + estado + ":");

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos con ese estado.");
            return;
        }

        for (Pedido p : pedidos) {
            System.out.printf("ID: %d | Fecha: %s | Total: $%.2f | Pago: %s%n",
                    p.getId(), p.getFecha().toLocalDate(), p.getTotal(), p.getFormaPago());
        }
    }

    // ===== REPORTES =====

    private static void menuReportes() {
        int opcion;

        do {
            System.out.println("\n===== REPORTES =====");
            System.out.println("1. Productos por categoria");
            System.out.println("2. Pedidos por usuario");
            System.out.println("3. Pedidos por estado");
            System.out.println("4. Total facturado (pedidos TERMINADO)");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> reporteProductosPorCategoria();
                case 2 -> reportePedidosPorUsuario();
                case 3 -> reportePedidosPorEstado();
                case 4 -> reporteTotalFacturado();
                case 0 -> System.out.println("Volviendo al menu principal.");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private static void reporteProductosPorCategoria() {
        System.out.println("\n--- Reporte: Productos por categoria ---");

        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas.");
            return;
        }

        listarCategorias();
        Long categoriaId = leerLong("Seleccione ID de categoria: ");

        Optional<Categoria> opt = categoriaRepository.buscarPorId(categoriaId);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: categoria no encontrada.");
            return;
        }

        List<Producto> productos = productoRepository.buscarPorCategoria(categoriaId);

        System.out.println("\nCategoria: " + opt.get().getNombre());
        System.out.println("Total de productos: " + productos.size());

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos en esa categoria.");
            return;
        }

        for (Producto p : productos) {
            System.out.printf("ID: %d | %-20s | Precio: %8.2f | Stock: %3d | %s%n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                    p.isDisponible() ? "Disponible" : "No disponible");
        }
    }

    private static void reportePedidosPorUsuario() {
        System.out.println("\n--- Reporte: Pedidos por usuario ---");

        listarUsuarios();
        Long usuarioId = leerLong("Seleccione ID del usuario: ");

        Optional<Usuario> opt = usuarioRepository.buscarPorId(usuarioId);

        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("Error: usuario no encontrado.");
            return;
        }

        List<Pedido> pedidos = pedidoRepository.buscarPorUsuario(usuarioId);
        Usuario u = opt.get();

        System.out.printf("\nUsuario: %s %s (%s)%n", u.getNombre(), u.getApellido(), u.getMail());
        System.out.println("Total de pedidos: " + pedidos.size());

        if (pedidos.isEmpty()) return;

        double totalGastado = 0;

        for (Pedido p : pedidos) {
            System.out.printf("ID: %d | Fecha: %s | Estado: %-12s | Total: $%.2f%n",
                    p.getId(), p.getFecha().toLocalDate(), p.getEstado(), p.getTotal());

            for (DetallePedido d : p.getDetalles()) {
                System.out.printf("   - %s x%d = $%.2f%n",
                        d.getProducto().getNombre(), d.getCantidad(), d.getSubtotal());
            }

            totalGastado += p.getTotal();
        }

        System.out.printf("Total gastado: $%.2f%n", totalGastado);
    }

    private static void reportePedidosPorEstado() {
        System.out.println("\n--- Reporte: Pedidos por estado ---");

        for (Estado e : Estado.values()) {
            List<Pedido> pedidos = pedidoRepository.buscarPorEstado(e);
            System.out.printf("%-12s: %d pedidos%n", e, pedidos.size());
        }

        System.out.println();
        System.out.println("Ver detalle de un estado especifico?");
        System.out.println("1=PENDIENTE  2=CONFIRMADO  3=TERMINADO  4=CANCELADO  0=Volver");
        int estadoNum = leerEntero("Seleccione: ");

        Estado estado = switch (estadoNum) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };

        if (estado == null) return;

        List<Pedido> pedidos = pedidoRepository.buscarPorEstado(estado);

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos con estado " + estado);
            return;
        }

        for (Pedido p : pedidos) {
            System.out.printf("ID: %d | Fecha: %s | Total: $%.2f | Pago: %s%n",
                    p.getId(), p.getFecha().toLocalDate(), p.getTotal(), p.getFormaPago());
        }
    }

    private static void reporteTotalFacturado() {
        System.out.println("\n--- Reporte: Total Facturado ---");
        System.out.println("(Solo pedidos en estado TERMINADO)");

        List<Pedido> terminados = pedidoRepository.buscarPorEstado(Estado.TERMINADO);

        if (terminados.isEmpty()) {
            System.out.println("No hay pedidos terminados.");
            System.out.println("Total facturado: $0.00");
            return;
        }

        double totalFacturado = terminados.stream()
                .mapToDouble(Pedido::getTotal)
                .sum();

        System.out.printf("Cantidad de pedidos terminados: %d%n", terminados.size());
        System.out.printf("Total facturado: $%.2f%n", totalFacturado);
    }

    // ===== UTILIDADES DE INPUT =====

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un ID valido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero decimal valido.");
            }
        }
    }
}
