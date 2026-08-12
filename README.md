# Food Store — Backend Spring Boot

**Trabajo Práctico — API REST — Programación IV — UTN TUPaD**
**Alumno:** Juan Pablo Rivero

---

## Descripción

Sistema de gestión de pedidos con API REST: usuarios, categorías, productos y pedidos (con sus detalles). El backend está armado en capas (controller → service → repository), con validaciones en los DTOs de entrada, manejo de errores con `@RestControllerAdvice`, persistencia con Spring Data JPA y documentación automática con Swagger.

---

## Tecnologías

| Tecnología          | Versión   |
|---------------------|-----------|
| Java                | 21        |
| Maven               | 3.9       |
| Spring Boot         | 3.5.9     |
| Spring Data JPA     | -         |
| Lombok              | -         |
| H2 Database         | -         |
| Swagger (springdoc) | 2.8.17    |

---

## Ejecución

Correr la aplicación con `./mvnw spring-boot:run` (o `mvn spring-boot:run` si Maven está instalado).

Con la aplicación corriendo:

- **Swagger UI:** http://localhost:8080/swagger-ui.html (también /swagger-ui/index.html)
- **Documentación JSON (OpenAPI):** http://localhost:8080/v3/api-docs
- **Consola H2:** http://localhost:8080/h2-console — JDBC URL `jdbc:h2:mem:foodstore`, usuario `sa`

---

## Endpoints

### Usuarios — `/api/usuarios`

| Método | Ruta                          | Descripción              |
|--------|-------------------------------|--------------------------|
| POST   | `/api/usuarios`               | Crea un usuario          |
| GET    | `/api/usuarios`               | Lista todos los usuarios |
| GET    | `/api/usuarios/{id}`          | Busca un usuario por id  |
| GET    | `/api/usuarios/mail/{mail}`   | Busca un usuario por mail |
| PUT    | `/api/usuarios/{id}`          | Actualiza un usuario     |

### Categorías — `/api/categorias`

| Método | Ruta                   | Descripción                       |
|--------|------------------------|-----------------------------------|
| POST   | `/api/categorias`      | Crea una categoría                |
| GET    | `/api/categorias`      | Lista todas las categorías        |
| GET    | `/api/categorias/{id}` | Busca una categoría por id        |
| PUT    | `/api/categorias/{id}` | Actualiza una categoría           |
| DELETE | `/api/categorias/{id}` | Elimina una categoría (lógico)    |

### Productos — `/api/productos`

| Método | Ruta                   | Descripción                       |
|--------|------------------------|-----------------------------------|
| POST   | `/api/productos`       | Crea un producto                  |
| GET    | `/api/productos`       | Lista todos los productos         |
| GET    | `/api/productos/{id}`  | Busca un producto por id          |
| PUT    | `/api/productos/{id}`  | Actualiza un producto             |
| DELETE | `/api/productos/{id}`  | Elimina un producto (lógico)      |

### Pedidos — `/api/pedidos`

| Método | Ruta                | Descripción                     |
|--------|---------------------|---------------------------------|
| POST   | `/api/pedidos`      | Crea un pedido con sus detalles |
| GET    | `/api/pedidos`      | Lista todos los pedidos         |
| GET    | `/api/pedidos/{id}` | Busca un pedido por id          |

> El total del pedido se calcula como la suma de los subtotales de sus detalles y el stock de cada producto se descuenta al crear el pedido. Las eliminaciones son lógicas (campo `eliminado`).

---

## Probar el flujo del TP con Postman/curl

En una base H2 nueva los ids empiezan en 1. Ejecutar los comandos en orden. También se puede importar la colección `postman/food-store-api.postman_collection.json` en Postman (File → Import) con todos los requests ya armados.

### 1) Crear 2 usuarios

```bash
curl -s -X POST http://localhost:8080/api/usuarios -H "Content-Type: application/json" -d '{"nombre":"Ana","apellido":"García","mail":"anagarcia@mail.com","celular":"11-5555-0101","contrasena":"admin123","rol":"ADMIN"}'
curl -s -X POST http://localhost:8080/api/usuarios -H "Content-Type: application/json" -d '{"nombre":"Carlos","apellido":"Pérez","mail":"carlosperez@mail.com","celular":"11-5555-0202","contrasena":"clave123","rol":"USUARIO"}'
```

### 2) Crear 3 categorías

```bash
curl -s -X POST http://localhost:8080/api/categorias -H "Content-Type: application/json" -d '{"nombre":"Hamburguesas","descripcion":"Clásicas y especiales, con pan artesanal"}'
curl -s -X POST http://localhost:8080/api/categorias -H "Content-Type: application/json" -d '{"nombre":"Pizzas","descripcion":"A la piedra, con ingredientes frescos"}'
curl -s -X POST http://localhost:8080/api/categorias -H "Content-Type: application/json" -d '{"nombre":"Bebidas","descripcion":"Gaseosas, aguas y jugos bien fríos"}'
```

### 3) Crear 10 productos (categoriaId 1, 2 y 3)

```bash
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Hamburguesa Clásica","precio":8500.0,"descripcion":"Medallón de carne, lechuga y tomate","stock":20,"imagen":"hamburguesa-clasica.jpg","disponible":true,"categoriaId":1}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Hamburguesa con Cheddar","precio":9500.0,"descripcion":"Medallón doble, cheddar extra y panceta","stock":15,"imagen":"hamburguesa-cheddar.jpg","disponible":true,"categoriaId":1}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Hamburguesa Veggie","precio":8200.0,"descripcion":"Medallón de garbanzos, rúcula y queso vegano","stock":10,"imagen":"hamburguesa-veggie.jpg","disponible":true,"categoriaId":1}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Pizza Margarita","precio":9800.0,"descripcion":"Mozzarella, tomate y albahaca fresca","stock":12,"imagen":"pizza-margarita.jpg","disponible":true,"categoriaId":2}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Pizza Calabresa","precio":10500.0,"descripcion":"Mozzarella, longaniza y aceitunas","stock":12,"imagen":"pizza-calabresa.jpg","disponible":true,"categoriaId":2}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Pizza Cuatro Quesos","precio":11200.0,"descripcion":"Mozzarella, roquefort, parmesano y provolone","stock":8,"imagen":"pizza-cuatro-quesos.jpg","disponible":true,"categoriaId":2}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Coca-Cola 500ml","precio":2500.0,"descripcion":"Gaseosa cola bien fría","stock":50,"imagen":"coca-cola-500.jpg","disponible":true,"categoriaId":3}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Agua Mineral 500ml","precio":1800.0,"descripcion":"Agua sin gas","stock":60,"imagen":"agua-500.jpg","disponible":true,"categoriaId":3}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Jugo de Naranja Natural","precio":3200.0,"descripcion":"Exprimido en el momento","stock":25,"imagen":"jugo-naranja.jpg","disponible":true,"categoriaId":3}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"nombre":"Cerveza Artesanal 473ml","precio":4200.0,"descripcion":"IPA rubia de producción local","stock":30,"imagen":"cerveza-473.jpg","disponible":true,"categoriaId":3}'
```

### 4) Crear 3 pedidos (cada uno con al menos 2 detalles)

El `subtotal` de cada detalle es `cantidad × precio` y el `total` del pedido es la suma de los subtotales.

```bash
curl -s -X POST http://localhost:8080/api/pedidos -H "Content-Type: application/json" -d '{"fecha":"2026-08-11T12:00:00","estado":"CONFIRMADO","formaPago":"TARJETA","usuarioId":1,"detalles":[{"productoId":1,"cantidad":2,"subtotal":17000.0},{"productoId":7,"cantidad":2,"subtotal":5000.0}]}'
curl -s -X POST http://localhost:8080/api/pedidos -H "Content-Type: application/json" -d '{"fecha":"2026-08-11T13:00:00","estado":"PENDIENTE","formaPago":"EFECTIVO","usuarioId":2,"detalles":[{"productoId":4,"cantidad":1,"subtotal":9800.0},{"productoId":5,"cantidad":1,"subtotal":10500.0},{"productoId":8,"cantidad":2,"subtotal":3600.0}]}'
curl -s -X POST http://localhost:8080/api/pedidos -H "Content-Type: application/json" -d '{"fecha":"2026-08-11T14:00:00","estado":"TERMINADO","formaPago":"TRANSFERENCIA","usuarioId":2,"detalles":[{"productoId":6,"cantidad":2,"subtotal":22400.0},{"productoId":9,"cantidad":1,"subtotal":3200.0},{"productoId":10,"cantidad":1,"subtotal":4200.0}]}'
```

### 5) Actualizar una categoría (PUT)

```bash
curl -s -X PUT http://localhost:8080/api/categorias/1 -H "Content-Type: application/json" -d '{"nombre":"Hamburguesas Gourmet","descripcion":"Hamburguesas premium con pan artesanal"}'
```

### 6) Buscar usuarios

```bash
curl -s http://localhost:8080/api/usuarios/1
curl -s http://localhost:8080/api/usuarios/mail/anagarcia@mail.com
```

### 7) Errores esperados

```bash
# 400 — validación (producto sin nombre)
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d '{"precio":8500.0,"stock":10,"categoriaId":1}'
# 404 — entidad inexistente
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/api/usuarios/99999
```

La respuesta de error tiene siempre el mismo formato:

```json
{"timestamp":"2026-08-11T12:05:00","status":404,"error":"Not Found","message":"Usuario no encontrado con id 99999","path":"/api/usuarios/99999"}
```
