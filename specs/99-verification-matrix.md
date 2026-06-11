# Matriz de Verificación — TPI Food Store

| ID  | Requisito                              | Fuente       | Archivo                          | Estado | Evidencia                           |
|-----|----------------------------------------|--------------|----------------------------------|--------|-------------------------------------|
| B01 | Backend Gradle (no Maven/Spring Boot) | Consigna     | build.gradle                     | DONE   | BUILD SUCCESSFUL con Gradle 9.5.1  |
| B02 | Java 21 + JPA/Hibernate 6.6.4         | Consigna     | build.gradle                     | DONE   | Dependencias verificadas            |
| B03 | H2 modo archivo (./data/jpa_db)       | Consigna     | persistence.xml                  | DONE   | Configurado foodstorePU             |
| B04 | persistence.xml con todas las entidades | Consigna   | META-INF/persistence.xml         | DONE   | 5 entidades registradas             |
| B05 | Package com.tp.jpa                     | Consigna     | src/main/java/com/tp/jpa/        | DONE   | Migrado desde org.example           |
| B06 | Base (@MappedSuperclass + baja lógica) | Consigna    | model/Base.java                  | DONE   | id, eliminado, createdAt            |
| B07 | Interface Calculable                   | Consigna     | model/Calculable.java            | DONE   | calcularTotal()                     |
| B08 | Entidad Categoria                      | Consigna     | model/Categoria.java             | DONE   | nombre, descripcion, Set<Producto>  |
| B09 | Entidad Producto                       | Consigna     | model/Producto.java              | DONE   | nombre, precio, stock, disponible   |
| B10 | Entidad Usuario                        | Consigna     | model/Usuario.java               | DONE   | mail único, rol, Set<Pedido>        |
| B11 | Entidad Pedido (implements Calculable) | Consigna    | model/Pedido.java                | DONE   | addDetallePedido, calcularTotal     |
| B12 | Entidad DetallePedido                  | Consigna     | model/DetallePedido.java         | DONE   | cantidad, subtotal, producto        |
| B13 | Enums Rol, Estado, FormaPago           | Consigna     | model/enums/                     | DONE   | 3 enums completos                   |
| B14 | BaseRepository<T> genérico             | Consigna     | repository/BaseRepository.java   | DONE   | guardar, buscarPorId, listar, baja  |
| B15 | CategoriaRepository                    | Consigna     | repository/CategoriaRepository.java | DONE | hereda de Base                    |
| B16 | ProductoRepository.buscarPorCategoria  | Consigna     | repository/ProductoRepository.java  | DONE | JPQL con JOIN c.productos          |
| B17 | UsuarioRepository.buscarPorMail        | Consigna     | repository/UsuarioRepository.java   | DONE | JPQL con :mail parameter           |
| B18 | PedidoRepository.buscarPorUsuario      | Consigna     | repository/PedidoRepository.java    | DONE | JPQL con JOIN u.pedidos            |
| B19 | PedidoRepository.buscarPorEstado       | Consigna     | repository/PedidoRepository.java    | DONE | JPQL con :estado parameter         |
| B20 | Menú Categorías (ABM + listado)        | Consigna     | Main.java                        | DONE   | Alta, modificar, baja, listado      |
| B21 | Menú Productos (ABM + listado)         | Consigna     | Main.java                        | DONE   | Alta, modificar, baja, listado      |
| B22 | Menú Usuarios (ABM + buscar por mail)  | Consigna     | Main.java                        | DONE   | Alta, modificar, baja, buscar       |
| B23 | Alta pedido ATÓMICA (una transacción)  | Consigna     | Main.java                        | DONE   | em.find + persist + commit/rollback |
| B24 | Stock se descuenta en alta pedido      | Consigna     | Main.java                        | DONE   | prod.setStock dentro de tx          |
| B25 | Cambiar estado de pedido               | Consigna     | Main.java                        | DONE   | opciones 1-4                        |
| B26 | Reporte: productos por categoría       | Consigna     | Main.java                        | DONE   | reporteProductosPorCategoria()      |
| B27 | Reporte: pedidos por usuario           | Consigna     | Main.java                        | DONE   | reportePedidosPorUsuario()          |
| B28 | Reporte: pedidos por estado            | Consigna     | Main.java                        | DONE   | reportePedidosPorEstado()           |
| B29 | Reporte: total facturado (solo TERMINADO) | Consigna  | Main.java                        | DONE   | reporteTotalFacturado()             |
| B30 | Input sin crashes (nextLine + try/catch) | Consigna  | Main.java                        | DONE   | leerEntero, leerLong, leerDouble    |
| F01 | Login con localStorage + seed admin    | Consigna     | utils/auth.ts                    | DONE   | admin@foodstore.local / admin123    |
| F02 | Guardar sesión en localStorage         | Consigna     | utils/auth.ts                    | DONE   | foodstore_session_v1                |
| F03 | Redirigir según rol                    | Consigna     | utils/auth.ts                    | DONE   | redirectAfterAuth()                 |
| F04 | Guards en páginas protegidas           | Consigna     | utils/auth.ts                    | DONE   | checkAuthUser, checkAuthTienda      |
| F05 | Catálogo con categorías (sidebar)      | Consigna     | client/home/home.ts              | DONE   | cargarCategorias()                  |
| F06 | Búsqueda en tiempo real                | Consigna     | client/home/home.ts              | DONE   | setupBusqueda() + onInput           |
| F07 | Filtro por categoría                   | Consigna     | client/home/home.ts              | DONE   | categoriaSeleccionada               |
| F08 | Ordenamiento A-Z, Z-A, precio asc/desc | Consigna    | client/home/home.ts              | DONE   | setupOrden() + ordenarLista()       |
| F09 | Detalle de producto                    | Consigna     | store/productDetail/             | DONE   | con stock, selector cantidad        |
| F10 | Carrito localStorage                   | Consigna     | utils/carritoStorage.ts          | DONE   | agregar, quitar, setCantidad        |
| F11 | Validar stock en carrito               | Consigna     | client/carrito/carrito.ts        | DONE   | revalidación antes de confirmar     |
| F12 | Forma de pago en confirmar pedido      | Consigna     | client/carrito/carrito.ts        | DONE   | selector EFECTIVO/TARJETA/TRANSF.   |
| F13 | Limpiar carrito al confirmar           | Consigna     | client/carrito/carrito.ts        | DONE   | vaciarCarrito() post-confirm        |
| F14 | Mis pedidos (solo del usuario)         | Consigna     | client/pedidos/pedidos.ts        | DONE   | getPedidosDeUsuario(userId)         |
| F15 | Admin Dashboard con estadísticas       | Consigna     | admin/dashboard/dashboard.ts     | DONE   | total cats, prods, pedidos, estados |
| F16 | Admin ABM Categorías                   | Consigna     | admin/categorias/                | DONE   | alta, editar, baja lógica           |
| F17 | Admin ABM Productos                    | Consigna     | admin/productos/                 | DONE   | alta, editar, baja + precio/stock   |
| F18 | Admin Pedidos (todos + filtro + estado) | Consigna   | admin/pedidos/                   | DONE   | filtro estado, cambiar estado       |
| D01 | README.md                              | Consigna     | README.md (backend)              | DONE   | cómo correr, usuarios, features     |
| D02 | docs/entrega-foodstore.md              | Consigna     | docs/entrega-foodstore.md        | DONE   | doc académica completa              |
| D03 | docs/video-script.md                   | Consigna     | docs/video-script.md             | DONE   | guion 10-15 min                     |
| D04 | Branch separado (no main/master)       | Consigna     | tpi-final-foodstore              | DONE   | ambos repos en branch correcto      |
