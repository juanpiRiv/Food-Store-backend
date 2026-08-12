# Food Store — Backend Spring Boot

**Trabajo Práctico — API REST — Programación IV — UTN TUPaD**
**Alumno:** Juan Pablo Rivero

---

## Descripción

API REST completa de gestión de pedidos de comida **Food Store**, construida con Spring Boot en arquitectura en capas (controller → service → repository), validaciones con Jakarta Bean Validation, manejo uniforme de errores con `@RestControllerAdvice`, persistencia con Spring Data JPA y documentación automática con Swagger (springdoc-openapi).

Los recursos expuestos son: **usuarios**, **categorías**, **productos** y **pedidos** (con sus detalles). La creación de datos se realiza a través de la API (con Postman o curl).

---

## Tecnologías

| Tecnología            | Versión   |
|-----------------------|-----------|
| Java                  | 21        |
| Maven                 | 3.9       |
| Spring Boot           | 3.5.9     |
| Spring Web            | -         |
| Spring Data JPA       | -         |
| Spring Validation     | -         |
| Lombok                | -         |
| H2 Database           | -         |
| Swagger (springdoc)   | 2.5.0     |

---

## Instalación y ejecución

```bash
# macOS / Linux
./mvnw spring-boot:run

# alternativa (si Maven está instalado globalmente)
mvn spring-boot:run
```

Con la aplicación corriendo:

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (también [swagger-ui.html](http://localhost:8080/swagger-ui.html))
- **Documentación JSON (OpenAPI):** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **Consola H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console) — JDBC URL `jdbc:h2:mem:foodstore`, usuario `sa`, contraseña vacía

---

## Estructura de paquetes

```
src/main/java/com/tp/foodstore/
├── FoodStoreApplication.java     ← clase principal
├── config/                       ← configuración (incluye OpenApiConfig con Swagger)
├── controller/                   ← controladores REST
├── service/
│   ├── interfaces/               ← contratos de servicios
│   └── impl/                     ← implementaciones de servicios
├── repository/                   ← repositorios Spring Data JPA
├── entity/                       ← entidades JPA
│   └── enums/                    ← enums del dominio (Estado, FormaPago, Rol)
├── dto/
│   ├── categoria/                ← DTOs de categorías
│   ├── detallePedido/            ← DTOs de detalle de pedidos
│   ├── pedido/                   ← DTOs de pedidos
│   ├── producto/                 ← DTOs de productos
│   └── usuario/                  ← DTOs de usuarios
├── mapper/                       ← conversión entre entidades y DTOs
├── exception/                    ← excepciones, ErrorResponse y AdviceController
└── util/                         ← clases de utilidad
```

---

## Endpoints

### Usuarios — `/api/usuarios`

| Método | Ruta              | Descripción                              | Body |
|--------|-------------------|------------------------------------------|------|
| POST   | `/api/usuarios`   | Crea un usuario                          | `UsuarioCreate` |
| GET    | `/api/usuarios`   | Lista todos los usuarios                 | - |
| GET    | `/api/usuarios/{id}` | Busca un usuario por id                | - |
| GET    | `/api/usuarios/mail/{mail}` | Busca un usuario por mail    | - |

### Categorías — `/api/categorias`

| Método | Ruta                | Descripción                        | Body |
|--------|---------------------|------------------------------------|------|
| POST   | `/api/categorias`   | Crea una categoría                 | `CategoriaCreate` |
| GET    | `/api/categorias`   | Lista todas las categorías         | - |
| GET    | `/api/categorias/{id}` | Busca una categoría por id      | - |
| PUT    | `/api/categorias/{id}` | Actualiza una categoría          | `CategoriaEdit` |
| DELETE | `/api/categorias/{id}` | Elimina una categoría (lógico)  | - |

### Productos — `/api/productos`

| Método | Ruta                | Descripción                        | Body |
|--------|---------------------|------------------------------------|------|
| POST   | `/api/productos`    | Crea un producto                   | `ProductoCreate` |
| GET    | `/api/productos`    | Lista todos los productos          | - |
| GET    | `/api/productos/{id}` | Busca un producto por id         | - |
| PUT    | `/api/productos/{id}` | Actualiza un producto            | `ProductoEdit` |
| DELETE | `/api/productos/{id}` | Elimina un producto (lógico)    | - |

### Pedidos — `/api/pedidos`

| Método | Ruta              | Descripción                        | Body |
|--------|-------------------|------------------------------------|------|
| POST   | `/api/pedidos`    | Crea un pedido con sus detalles    | `PedidoEdit` |
| GET    | `/api/pedidos`    | Lista todos los pedidos            | - |
| GET    | `/api/pedidos/{id}` | Busca un pedido por id           | - |

> El total del pedido se calcula automáticamente como la suma de los subtotales de sus detalles. Las eliminaciones son lógicas (campo `eliminado`): las entidades eliminadas dejan de aparecer en las consultas.

---

## Probar con Postman / curl

Los siguientes comandos reproducen el flujo completo del TP. En una base H2 nueva los ids empiezan en 1; si ya creaste datos, usá los ids que devuelve cada respuesta.

### 1) Categorías (3)

```bash
curl -s -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Hamburguesas","descripcion":"Clásicas y especiales, con pan artesanal"}'

curl -s -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Pizzas","descripcion":"A la piedra, con ingredientes frescos"}'

curl -s -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Bebidas","descripcion":"Gaseosas, aguas y jugos bien fríos"}'
```

### 2) Usuarios (2)

```bash
curl -s -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","apellido":"García","mail":"anagarcia@mail.com","celular":"11-5555-0101","contrasena":"admin123","rol":"ADMIN"}'

curl -s -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Carlos","apellido":"Pérez","mail":"carlosperez@mail.com","celular":"11-5555-0202","contrasena":"clave123","rol":"USUARIO"}'
```

### 3) Productos (10, distribuidos en las 3 categorías)

```bash
# Hamburguesas (categoría 1)
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Hamburguesa Clásica","precio":8500.0,"descripcion":"Medallón de carne, lechuga, tomate y mayonesa casera","stock":20,"imagen":"hamburguesa-clasica.jpg","disponible":true,"categoriaId":1}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Hamburguesa con Cheddar","precio":9500.0,"descripcion":"Medallón doble, cheddar extra y panceta","stock":15,"imagen":"hamburguesa-cheddar.jpg","disponible":true,"categoriaId":1}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Hamburguesa Veggie","precio":8200.0,"descripcion":"Medallón de garbanzos, rúcula y queso vegano","stock":10,"imagen":"hamburguesa-veggie.jpg","disponible":true,"categoriaId":1}'

# Pizzas (categoría 2)
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Pizza Margarita","precio":9800.0,"descripcion":"Mozzarella, tomate y albahaca fresca","stock":12,"imagen":"pizza-margarita.jpg","disponible":true,"categoriaId":2}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Pizza Calabresa","precio":10500.0,"descripcion":"Mozzarella, longaniza y aceitunas","stock":12,"imagen":"pizza-calabresa.jpg","disponible":true,"categoriaId":2}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Pizza Cuatro Quesos","precio":11200.0,"descripcion":"Mozzarella, roquefort, parmesano y provolone","stock":8,"imagen":"pizza-cuatro-quesos.jpg","disponible":true,"categoriaId":2}'

# Bebidas (categoría 3)
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Coca-Cola 500ml","precio":2500.0,"descripcion":"Gaseosa cola bien fría","stock":50,"imagen":"coca-cola-500.jpg","disponible":true,"categoriaId":3}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Agua Mineral 500ml","precio":1800.0,"descripcion":"Agua sin gas","stock":60,"imagen":"agua-500.jpg","disponible":true,"categoriaId":3}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Jugo de Naranja Natural","precio":3200.0,"descripcion":"Exprimido en el momento","stock":25,"imagen":"jugo-naranja.jpg","disponible":true,"categoriaId":3}'
curl -s -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" \
  -d '{"nombre":"Cerveza Artesanal 473ml","precio":4200.0,"descripcion":"IPA rubia de producción local","stock":30,"imagen":"cerveza-473.jpg","disponible":true,"categoriaId":3}'
```

### 4) Pedidos (3, cada uno con al menos 2 detalles)

El `subtotal` de cada detalle es `cantidad × precio` y el `total` del pedido se calcula como la suma de los subtotales.

```bash
# Pedido 1 — Ana (usuario 1): 2x Hamburguesa Clásica + 2x Coca-Cola 500ml (total 22000)
curl -s -X POST http://localhost:8080/api/pedidos -H "Content-Type: application/json" \
  -d '{"fecha":"2026-08-11T12:00:00","estado":"CONFIRMADO","formaPago":"TARJETA","usuarioId":1,
       "detalles":[{"productoId":1,"cantidad":2,"subtotal":17000.0},
                   {"productoId":7,"cantidad":2,"subtotal":5000.0}]}'

# Pedido 2 — Carlos (usuario 2): 1x Pizza Margarita + 1x Pizza Calabresa + 2x Agua Mineral (total 23900)
curl -s -X POST http://localhost:8080/api/pedidos -H "Content-Type: application/json" \
  -d '{"fecha":"2026-08-11T13:00:00","estado":"PENDIENTE","formaPago":"EFECTIVO","usuarioId":2,
       "detalles":[{"productoId":4,"cantidad":1,"subtotal":9800.0},
                   {"productoId":5,"cantidad":1,"subtotal":10500.0},
                   {"productoId":8,"cantidad":2,"subtotal":3600.0}]}'

# Pedido 3 — Carlos (usuario 2): 2x Pizza Cuatro Quesos + 1x Jugo de Naranja + 1x Cerveza (total 29800)
curl -s -X POST http://localhost:8080/api/pedidos -H "Content-Type: application/json" \
  -d '{"fecha":"2026-08-11T14:00:00","estado":"TERMINADO","formaPago":"TRANSFERENCIA","usuarioId":2,
       "detalles":[{"productoId":6,"cantidad":2,"subtotal":22400.0},
                   {"productoId":9,"cantidad":1,"subtotal":3200.0},
                   {"productoId":10,"cantidad":1,"subtotal":4200.0}]}'
```

### 5) Actualizar una categoría (PUT)

```bash
curl -s -X PUT http://localhost:8080/api/categorias/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Hamburguesas Gourmet","descripcion":"Hamburguesas premium con pan artesanal"}'
```

### 6) Buscar usuarios

```bash
# Por id
curl -s http://localhost:8080/api/usuarios/1

# Por mail
curl -s http://localhost:8080/api/usuarios/mail/anagarcia@mail.com
```

### 7) Errores

```bash
# 400 — validación (producto sin nombre)
curl -s -o /dev/null -w "%{http_code}\n" -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"precio":8500.0,"stock":10,"categoriaId":1}'

# 404 — entidad inexistente (AdviceController)
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/api/usuarios/99999
```

La respuesta de error tiene siempre el mismo formato:

```json
{
  "timestamp": "2026-08-11T12:05:00",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario no encontrado con id 99999",
  "path": "/api/usuarios/99999"
}
```

---

## Responsabilidad de cada paquete

| Paquete      | Responsabilidad |
|--------------|-----------------|
| config       | Beans y configuración general; `OpenApiConfig` define la metadata de Swagger |
| controller   | Expone los endpoints de la API REST; delega toda la lógica en los servicios |
| service      | Contratos e implementaciones de la lógica de negocio |
| repository   | Acceso a datos con Spring Data JPA |
| entity       | Entidades JPA del dominio junto con sus enums en `entity/enums/` |
| dto          | Objetos de transferencia de datos agrupados por módulo (con validaciones en los de entrada) |
| mapper       | Conversión entre entidades y DTOs |
| exception    | Excepciones de negocio, `ErrorResponse` y manejo global de errores (`AdviceController`) |
| util         | Utilidades de propósito general |
