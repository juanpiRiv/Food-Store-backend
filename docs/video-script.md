# Guion para Video Demo — Food Store TPI

**Duración estimada**: 12-15 minutos

---

## 1. Introducción (1 min)

> "Hola, mi nombre es Juan Pablo Rivero. Voy a presentar el Trabajo Práctico Integrador de Programación III: Food Store. Es un sistema de ecommerce de comida con un backend de consola en Java con JPA/Hibernate y un frontend web en TypeScript/Vite."

- Mostrar estructura de carpetas brevemente
- Mencionar las tecnologías

---

## 2. Tecnologías (30 seg)

**Backend:**
- Java 21 + Gradle
- JPA/Hibernate 6.6.4
- H2 Database modo archivo
- Menú de consola (sin Spring Boot, sin API REST)

**Frontend:**
- TypeScript + Vite
- HTML/CSS puro
- localStorage para autenticación y datos

---

## 3. Demo Frontend — Login (1.5 min)

1. Abrir `http://localhost:5173`
2. Mostrar que redirige a login
3. Intentar entrar sin credenciales → ver validación
4. Ingresar con **admin@foodstore.local / admin123**
5. Mostrar redirección al panel admin
6. Logout
7. Registrar un nuevo usuario cliente
8. Login con el usuario cliente → redirección a la tienda

---

## 4. Demo Frontend — Catálogo Cliente (2 min)

1. Mostrar el catálogo con todos los productos
2. Usar el buscador en tiempo real ("hamb...")
3. Filtrar por categoría "Pizzas"
4. Cambiar ordenamiento a "Precio mayor a menor"
5. Mostrar badge de stock en las cards
6. Mostrar producto sin stock/no disponible
7. Click en "Ver detalle" de un producto

---

## 5. Demo Frontend — Detalle de Producto (1 min)

1. Ver imagen grande, descripción, precio, stock
2. Cambiar la cantidad en el selector
3. Intentar superar el stock → ver validación
4. Click en "Agregar al carrito"
5. Verificar que el contador del carrito se actualiza

---

## 6. Demo Frontend — Carrito (1.5 min)

1. Agregar 2-3 productos desde el catálogo
2. Abrir carrito
3. Modificar cantidad con + y -
4. Intentar + más allá del stock → ver validación
5. Quitar un producto
6. Cambiar forma de pago (Tarjeta)
7. Click en "Confirmar pedido"
8. Ver redirección a Mis Pedidos

---

## 7. Demo Frontend — Mis Pedidos (1 min)

1. Ver el pedido recién creado con estado PENDIENTE
2. Ver los detalles (ítems, forma de pago, total)
3. Mostrar el badge de color según estado

---

## 8. Demo Frontend — Panel Admin (2 min)

1. Volver a login como admin
2. **Dashboard**: ver estadísticas (total categorías, productos, pedidos, disponibles)
3. **Categorías**: mostrar tabla, crear una categoría nueva, editarla
4. **Productos**: mostrar tabla, crear un producto nuevo con stock
5. **Pedidos**: ver todos los pedidos de todos los usuarios, cambiar estado del pedido reciente a CONFIRMADO

---

## 9. Demo Backend — Consola (3 min)

```bash
./gradlew run
```

1. **Alta de categoría**: ingresar "Combos" con descripción
2. **Alta de producto**: crear "Combo Burger" en la categoría recién creada, precio 6500, stock 10
3. **Alta de usuario**: crear un usuario con rol USUARIO
4. **Alta de pedido**:
   - Seleccionar el usuario creado
   - Forma de pago TARJETA
   - Agregar "Combo Burger" × 2
   - Confirmar → ver que el stock se descuenta de 10 a 8
5. **Cambiar estado a TERMINADO**
6. **Reporte total facturado** → ver que muestra el total del pedido

---

## 10. Demo Backend — Reportes (1 min)

1. Reporte de productos por categoría → seleccionar "Combos"
2. Reporte de pedidos por usuario → ver el pedido reciente
3. Reporte de pedidos por estado → ver resumen
4. Reporte de total facturado → ver monto con 2 decimales

---

## 11. Cierre (30 seg)

> "Eso es todo. El proyecto implementa correctamente JPA/Hibernate con relaciones entre entidades, transacciones, consultas JPQL, y un frontend modular en TypeScript. Gracias."

- Mostrar que el build del backend y frontend pasan sin errores

---

## Notas para la grabación

- Hablar claro y a ritmo moderado
- Mostrar la pantalla completa (no solo la zona de código)
- Pausar brevemente después de cada demostración importante
- Objetivo: 12-15 minutos máximo
