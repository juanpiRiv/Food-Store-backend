# Food Store — Documentación Académica TPI

**Materia**: Programación III
**Institución**: UTN
**Alumno**: Juan Pablo Rivero
**Fecha de entrega**: Junio 2026

---

## Introducción

Este Trabajo Práctico Integrador consiste en el desarrollo de un sistema de ecommerce de comida llamado **Food Store**. El proyecto está dividido en dos componentes:

1. **Backend de consola**: aplicación Java que persiste datos mediante JPA/Hibernate en una base H2.
2. **Frontend web**: aplicación TypeScript/Vite que permite a clientes y administradores interactuar visualmente con el sistema.

---

## Objetivo

Aplicar los conocimientos de **JPA/Hibernate**, **relaciones entre entidades**, **transacciones**, y **consultas JPQL** en un proyecto de escala mediana, junto con un frontend funcional que demuestre el flujo completo de un ecommerce.

---

## Arquitectura

### Backend

```
Main.java (menú de consola)
    ↓
Repositories (CategoriaRepository, ProductoRepository, UsuarioRepository, PedidoRepository)
    ↓
BaseRepository<T> (genérico con guardar, buscarPorId, listarActivos, eliminarLogico)
    ↓
JpaUtil (Singleton EntityManagerFactory)
    ↓
Hibernate ORM + H2 File Database
```

**Decisiones de diseño:**
- `@MappedSuperclass` en `Base.java` para reutilizar id, eliminado, createdAt.
- Baja lógica en lugar de DELETE físico.
- Alta de pedido en una única transacción JPA con rollback automático si falla.
- Stock se descuenta dentro de la transacción, garantizando consistencia.

### Frontend

```
Páginas HTML (multi-page Vite app)
    ↓
TypeScript Modules (utils: auth, navigate, cart, storage)
    ↓
localStorage (datos, sesión, carrito, pedidos)
```

---

## Modelo de Dominio

| Entidad | Descripción |
|---|---|
| Base | Superclase abstracta con id, eliminado, createdAt |
| Categoria | Agrupa productos |
| Producto | Ítem a la venta con stock y disponibilidad |
| Usuario | Persona con rol ADMIN o USUARIO |
| Pedido | Orden de compra con estado y forma de pago |
| DetallePedido | Línea de un pedido (cantidad × producto) |

---

## Funcionalidades Implementadas

### Backend
- ABM completo (Alta, Baja Lógica, Modificación, Listado) para las 4 entidades principales
- Alta de pedido con transacción única, validación de stock y rollback si falla
- Cambio de estado de pedidos (PENDIENTE → CONFIRMADO → TERMINADO / CANCELADO)
- Búsqueda de usuario por mail
- 4 reportes: productos por categoría, pedidos por usuario, pedidos por estado, total facturado

### Frontend
- Autenticación con roles (admin seed automático)
- Catálogo con búsqueda en tiempo real, filtro por categoría y ordenamiento
- Detalle de producto con selector de cantidad
- Carrito con validación de stock y forma de pago
- Mis pedidos con estado visible
- Panel admin completo (dashboard + ABM + gestión de pedidos)

---

## Capturas de Pantalla

> [Agregar capturas de pantalla aquí]

---

## Dificultades y Soluciones

1. **Migración de paquete** (`org.example` → `com.tp.jpa`): Se recrearon todos los archivos bajo la nueva estructura.

2. **Alta de pedido atómica**: La principal complejidad fue garantizar que el EntityManager fuera único para toda la transacción. Se resolvió con un `EntityManager` manual en `Main.java` para este flujo específico.

3. **Tipos de frontend**: Al agregar `stock` y `disponible` al tipo `Producto`, fue necesario actualizar todas las referencias en los módulos de datos y utilities.

4. **Java 23 no disponible**: La consigna menciona Java 23 pero el entorno tiene Java 21. Se usó Java 21 que es compatible y estable.

---

## Conclusión

El proyecto implementa exitosamente un sistema de ecommerce académico que cumple con todos los requisitos de la consigna. El backend demuestra el uso correcto de JPA/Hibernate con relaciones, transacciones y consultas JPQL. El frontend demuestra una arquitectura modular con TypeScript puro sin frameworks adicionales.

---

## Bibliografía

- Documentación de Hibernate ORM: https://hibernate.org/orm/documentation
- Documentación de JPA (Jakarta Persistence): https://jakarta.ee/specifications/persistence
- Documentación de Vite: https://vitejs.dev
- Documentación de TypeScript: https://www.typescriptlang.org/docs
