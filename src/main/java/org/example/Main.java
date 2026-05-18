package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.Categoria;
import org.example.model.Pedido;
import org.example.model.Producto;
import org.example.model.Usuario;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            limpiarBase(em);

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

            em.persist(bebidas);
            em.persist(snacks);
            em.persist(limpieza);

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

            em.persist(usuario1);
            em.persist(usuario2);

            em.flush();

            coca.setPrecio(1250.0);
            coca.setStock(20);

            papas.setPrecio(1500.0);
            papas.setDisponible(false);

            Usuario usuarioPorId = em.find(Usuario.class, usuario1.getId());
            System.out.println("Usuario por ID: " + usuarioPorId);

            Usuario usuarioPorMail = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :email",
                            Usuario.class
                    )
                    .setParameter("email", "ana@mail.com")
                    .getSingleResult();

            System.out.println("Usuario por mail: " + usuarioPorMail);

            limpieza.eliminarProducto(esponja);
            em.remove(esponja);

            em.flush();

            Long cantidadUsuarios = em.createQuery(
                    "SELECT COUNT(u) FROM Usuario u",
                    Long.class
            ).getSingleResult();

            Long cantidadProductos = em.createQuery(
                    "SELECT COUNT(p) FROM Producto p",
                    Long.class
            ).getSingleResult();

            Long cantidadCategorias = em.createQuery(
                    "SELECT COUNT(c) FROM Categoria c",
                    Long.class
            ).getSingleResult();

            Long cantidadPedidos = em.createQuery(
                    "SELECT COUNT(p) FROM Pedido p",
                    Long.class
            ).getSingleResult();

            Long cantidadDetalles = em.createQuery(
                    "SELECT COUNT(d) FROM DetallePedido d",
                    Long.class
            ).getSingleResult();

            System.out.println("=== RESUMEN FINAL DE PERSISTENCIA ===");
            System.out.println("Usuarios persistidos: " + cantidadUsuarios);
            System.out.println("Productos persistidos: " + cantidadProductos);
            System.out.println("Categorias persistidas: " + cantidadCategorias);
            System.out.println("Pedidos persistidos: " + cantidadPedidos);
            System.out.println("Detalles persistidos: " + cantidadDetalles);

            em.getTransaction().commit();

            System.out.println("TP JPA ejecutado correctamente.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
            emf.close();
        }
    }

    private static void limpiarBase(EntityManager em) {
        em.createQuery("DELETE FROM DetallePedido").executeUpdate();
        em.createQuery("DELETE FROM Pedido").executeUpdate();
        em.createQuery("DELETE FROM Producto").executeUpdate();
        em.createQuery("DELETE FROM Categoria").executeUpdate();
        em.createQuery("DELETE FROM Usuario").executeUpdate();
    }

    private static Producto crearProducto(
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

    private static Pedido crearPedido(Estado estado, FormaPago formaPago) {
        return Pedido.builder()
                .fecha(LocalDate.now())
                .estado(estado)
                .formaPago(formaPago)
                .total(0.0)
                .build();
    }
}