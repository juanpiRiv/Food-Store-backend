# Food Store — Backend JPA / Consola

**Trabajo Final Integrador — Programación III — UTN TUPaD**
**Alumno:** Juan Pablo Rivero

---

## Descripción

Aplicación de consola para el sistema de gestión de pedidos de comida **Food Store**. Gestiona categorías, productos, usuarios y pedidos con persistencia real en base de datos H2 mediante JPA/Hibernate.

Sin Spring Boot. Sin API REST. Interacción exclusivamente por consola, con menús navegables.

---

## Tecnologías

| Tecnología       | Versión   |
|------------------|-----------|
| Java             | 21        |
| Gradle           | 8.x       |
| Hibernate ORM    | 6.6.4     |
| Jakarta JPA      | 3.x       |
| H2 Database      | 2.3.232   |
| Lombok           | 1.18.44   |

---

## Instalación y ejecución

```bash
# macOS / Linux
./gradlew run

# Windows
gradlew.bat run
```

La base de datos H2 se crea automáticamente en `./data/jpa_db` al ejecutar por primera vez. No requiere instalación de base de datos externa.

---

## Estructura de paquetes

```
src/main/java/com/tp/jpa/
├── Main.java                   ← menú principal de consola
├── model/
│   ├── Base.java               ← @MappedSuperclass con id, eliminado, createdAt
│   ├── Calculable.java         ← interfaz con calcularTotal()
│   ├── Categoria.java
│   ├── Producto.java
│   ├── Usuario.java
│   ├── Pedido.java             ← implementa Calculable
│   ├── DetallePedido.java
│   └── enums/
│       ├── Estado.java         ← PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO
│       ├── FormaPago.java      ← TARJETA, TRANSFERENCIA, EFECTIVO
│       └── Rol.java            ← ADMIN, USUARIO
├── repository/
│   ├── BaseRepository.java     ← CRUD genérico para todas las entidades
│   ├── CategoriaRepository.java
│   ├── ProductoRepository.java ← buscarPorCategoria() con JPQL
│   ├── UsuarioRepository.java  ← buscarPorMail() con JPQL
│   └── PedidoRepository.java   ← buscarPorUsuario(), buscarPorEstado() con JPQL
└── util/
    └── JpaUtil.java            ← Singleton del EntityManagerFactory

src/main/resources/META-INF/persistence.xml
```

---

## Menú principal

```
1. Gestionar Categorías   → ABM completo con baja lógica
2. Gestionar Productos    → ABM con validación de precio/stock y categoría
3. Gestionar Usuarios     → ABM con unicidad de mail y búsqueda por mail
4. Gestionar Pedidos      → Alta atómica, cambio de estado, baja lógica
5. Reportes               → Productos por categoría, pedidos por usuario/estado, total facturado
0. Salir                  → cierra EntityManagerFactory correctamente
```

---

## Decisiones técnicas clave

- **Transacción atómica en Alta de Pedido:** todo el alta (validación de stock, descuento de inventario, persistencia del pedido y sus detalles) ocurre en una única transacción. Cualquier falla hace rollback completo.
- **Baja lógica:** ningún registro se borra físicamente. El campo `eliminado = true` lo excluye de todos los listados activos.
- **BaseRepository genérico:** una sola clase abstracta implementa `guardar()`, `buscarPorId()`, `listarActivos()` y `eliminarLogico()` para todas las entidades.
- **JPQL dinámico:** la consulta de `listarActivos()` usa `entityClass.getSimpleName()` para funcionar con cualquier entidad sin duplicar código.
- **Singleton JpaUtil:** el `EntityManagerFactory` se crea una sola vez y se cierra al salir de la aplicación.
