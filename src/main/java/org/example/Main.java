package org.example;

import org.example.model.Categoria;
import org.example.model.Producto;
import org.example.repository.CategoriaRepository;
import org.example.repository.ProductoRepository;
import org.example.util.JpaUtil;

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
                    case 3 -> menuReportes();
                    case 0 -> System.out.println("Programa finalizado.");
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcion != 0);
        } finally {
            // al salir libero la factory y cierro el scanner si o si
            JpaUtil.cerrar();
            sc.close();
        }
    }

    private static void inicializarRepositorios() {
        categoriaRepository = new CategoriaRepository();
        productoRepository = new ProductoRepository();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Categorias");
        System.out.println("2. Productos");
        System.out.println("3. Reportes");
        System.out.println("0. Salir");
    }

    private static void menuCategorias() {
        int opcion;

        do {
            System.out.println("\n===== ABM CATEGORIAS =====");
            System.out.println("1. Alta");
            System.out.println("2. Baja logica");
            System.out.println("3. Modificacion");
            System.out.println("4. Listado");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> altaCategoria();
                case 2 -> bajaCategoria();
                case 3 -> modificarCategoria();
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

        System.out.println("Categoria creada correctamente.");
        System.out.println("ID generado: " + guardada.getId());
    }

    private static void bajaCategoria() {
        System.out.println("\n--- Baja logica de categoria ---");

        listarCategorias();

        Long id = leerLong("Ingrese ID de la categoria: ");

        Optional<Categoria> categoriaOpt = categoriaRepository.buscarPorId(id);

        if (categoriaOpt.isEmpty() || categoriaOpt.get().isEliminado()) {
            System.out.println("Error: la categoria no existe o ya esta dada de baja.");
            return;
        }

        Categoria categoria = categoriaOpt.get();
        boolean eliminada = categoriaRepository.eliminarLogico(id);

        if (eliminada) {
            System.out.println("Categoria dada de baja correctamente: " + categoria.getNombre());
        } else {
            System.out.println("No se pudo dar de baja la categoria.");
        }
    }

    private static void modificarCategoria() {
        System.out.println("\n--- Modificacion de categoria ---");

        listarCategorias();

        Long id = leerLong("Ingrese ID de la categoria: ");

        Optional<Categoria> categoriaOpt = categoriaRepository.buscarPorId(id);

        if (categoriaOpt.isEmpty() || categoriaOpt.get().isEliminado()) {
            System.out.println("Error: no existe una categoria activa con ese ID.");
            return;
        }

        Categoria categoria = categoriaOpt.get();

        System.out.println("Valores actuales:");
        System.out.println("Nombre: " + categoria.getNombre());
        System.out.println("Descripcion: " + categoria.getDescripcion());

        String nuevoNombre = leerTexto("Nuevo nombre, enter para conservar: ");
        String nuevaDescripcion = leerTexto("Nueva descripcion, enter para conservar: ");

        if (!nuevoNombre.isBlank()) {
            categoria.setNombre(nuevoNombre);
        }

        if (!nuevaDescripcion.isBlank()) {
            categoria.setDescripcion(nuevaDescripcion);
        }

        categoriaRepository.guardar(categoria);

        System.out.println("Categoria modificada correctamente.");
    }

    private static void listarCategorias() {
        System.out.println("\n--- Categorias activas ---");

        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas.");
            return;
        }

        for (Categoria c : categorias) {
            System.out.printf(
                    "ID: %d | Nombre: %s | Descripcion: %s%n",
                    c.getId(),
                    c.getNombre(),
                    c.getDescripcion()
            );
        }
    }

    private static void menuProductos() {
        int opcion;

        do {
            System.out.println("\n===== ABM PRODUCTOS =====");
            System.out.println("1. Alta");
            System.out.println("2. Baja logica");
            System.out.println("3. Modificacion");
            System.out.println("4. Listado");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> altaProducto();
                case 2 -> bajaProducto();
                case 3 -> modificarProducto();
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
            System.out.println("No hay categorias activas. No se puede crear el producto.");
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
        int stock = leerEntero("Stock: ");

        // no dejo entrar datos invalidos a la base: corto antes de guardar
        if (precio <= 0) {
            System.out.println("Error: el precio debe ser mayor a 0.");
            return;
        }

        if (stock < 0) {
            System.out.println("Error: el stock no puede ser negativo.");
            return;
        }

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoriaOpt.get());
        producto.setEliminado(false);
        producto.setDisponible(true);

        Producto guardado = productoRepository.guardar(producto);

        System.out.println("Producto creado correctamente.");
        System.out.println("ID generado: " + guardado.getId());
        System.out.println("Categoria asignada: " + categoriaOpt.get().getNombre());
    }

    private static void bajaProducto() {
        System.out.println("\n--- Baja logica de producto ---");

        listarProductos();

        Long id = leerLong("Ingrese ID del producto: ");

        Optional<Producto> productoOpt = productoRepository.buscarPorId(id);

        // me fijo que exista y que no este ya dado de baja antes de tocar nada
        if (productoOpt.isEmpty() || productoOpt.get().isEliminado()) {
            System.out.println("Error: el producto no existe o ya esta dado de baja.");
            return;
        }

        Producto producto = productoOpt.get();
        boolean eliminado = productoRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("Producto dado de baja correctamente: " + producto.getNombre());
        } else {
            System.out.println("No se pudo dar de baja el producto.");
        }
    }

    private static void modificarProducto() {
        System.out.println("\n--- Modificacion de producto ---");

        listarProductos();

        Long id = leerLong("Ingrese ID del producto: ");

        Optional<Producto> productoOpt = productoRepository.buscarPorId(id);

        if (productoOpt.isEmpty() || productoOpt.get().isEliminado()) {
            System.out.println("Error: no existe un producto activo con ese ID.");
            return;
        }

        Producto producto = productoOpt.get();

        System.out.println("Valores actuales:");
        System.out.println("Nombre: " + producto.getNombre());
        System.out.println("Precio: " + producto.getPrecio());
        System.out.println("Stock: " + producto.getStock());

        String nuevoNombre = leerTexto("Nuevo nombre, enter para conservar: ");
        String nuevoPrecioTexto = leerTexto("Nuevo precio, enter para conservar: ");
        String nuevoStockTexto = leerTexto("Nuevo stock, enter para conservar: ");

        if (!nuevoNombre.isBlank()) {
            producto.setNombre(nuevoNombre);
        }

        if (!nuevoPrecioTexto.isBlank()) {
            double nuevoPrecio;

            try {
                nuevoPrecio = Double.parseDouble(nuevoPrecioTexto);
            } catch (NumberFormatException e) {
                System.out.println("Error: precio invalido.");
                return;
            }

            if (nuevoPrecio <= 0) {
                System.out.println("Error: el precio debe ser mayor a 0.");
                return;
            }

            producto.setPrecio(nuevoPrecio);
        }

        if (!nuevoStockTexto.isBlank()) {
            int nuevoStock;

            try {
                nuevoStock = Integer.parseInt(nuevoStockTexto);
            } catch (NumberFormatException e) {
                System.out.println("Error: stock invalido.");
                return;
            }

            if (nuevoStock < 0) {
                System.out.println("Error: el stock no puede ser negativo.");
                return;
            }

            producto.setStock(nuevoStock);
        }

        productoRepository.guardar(producto);

        System.out.println("Producto modificado correctamente.");
    }

    private static void listarProductos() {
        System.out.println("\n--- Productos activos ---");

        List<Producto> productos = productoRepository.listarActivos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos.");
            return;
        }

        for (Producto p : productos) {
            // si el producto no tiene categoria muestro un texto por defecto
            String nombreCategoria = p.getCategoria() != null
                    ? p.getCategoria().getNombre()
                    : "Sin categoria";

            System.out.printf(
                    "ID: %d | Nombre: %s | Precio: %.2f | Stock: %d | Categoria: %s%n",
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock(),
                    nombreCategoria
            );
        }
    }

    private static void menuReportes() {
        int opcion;

        do {
            System.out.println("\n===== REPORTES =====");
            System.out.println("1. Productos por categoria");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> productosPorCategoria();
                case 0 -> System.out.println("Volviendo al menu principal.");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static void productosPorCategoria() {
        System.out.println("\n--- Productos por categoria ---");

        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas.");
            return;
        }

        listarCategorias();

        Long categoriaId = leerLong("Seleccione ID de categoria: ");

        Optional<Categoria> categoriaOpt = categoriaRepository.buscarPorId(categoriaId);

        if (categoriaOpt.isEmpty() || categoriaOpt.get().isEliminado()) {
            System.out.println("Error: categoria inexistente o dada de baja.");
            return;
        }

        List<Producto> productos = productoRepository.buscarPorCategoria(categoriaId);

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos en esa categoria.");
            return;
        }

        System.out.println("Categoria: " + categoriaOpt.get().getNombre());

        for (Producto p : productos) {
            System.out.printf(
                    "ID: %d | Nombre: %s | Precio: %.2f | Stock: %d%n",
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock()
            );
        }
    }

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