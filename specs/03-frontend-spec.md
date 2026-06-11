# Frontend — Especificación

## Stack
- TypeScript + Vite (multi-page app)
- HTML/CSS puro, sin frameworks
- localStorage para estado, carrito, pedidos
- Autenticación educativa (no JWT, no backend)

## Estructura de Páginas

| Ruta                           | Descripción                        |
|--------------------------------|------------------------------------|
| `src/pages/auth/login/`        | Login (email + contraseña)         |
| `src/pages/auth/registro/`     | Registro de nuevo usuario          |
| `src/pages/client/home/`       | Catálogo con filtros y búsqueda    |
| `src/pages/client/carrito/`    | Carrito con forma de pago          |
| `src/pages/client/pedidos/`    | Mis pedidos (solo del usuario)     |
| `src/pages/store/productDetail/` | Detalle de un producto           |
| `src/pages/admin/dashboard/`   | Dashboard con estadísticas         |
| `src/pages/admin/categorias/`  | ABM Categorías                     |
| `src/pages/admin/productos/`   | ABM Productos                      |
| `src/pages/admin/pedidos/`     | Todos los pedidos, cambiar estado  |

## Autenticación
- `usuarios.json` + localStorage como fuente de usuarios
- Usuario por defecto: `admin@foodstore.local` / `admin123`
- Guardar sesión en `foodstore_session_v1`
- Redirigir según rol: ADMIN → dashboard, USUARIO → clientHome
- Guards protegen cada página

## Catálogo (Home Cliente)
- Filtro por categoría (sidebar)
- Búsqueda en tiempo real por nombre/descripción
- Ordenamiento: A-Z, Z-A, precio asc, precio desc
- Badge de stock en cada card
- Link al detalle del producto
- Botón "Agregar al carrito" (disabled si sin stock)

## Carrito
- Agregar, modificar cantidad (+/-), quitar línea, vaciar
- Validar stock antes de confirmar (no se puede exceder stock real)
- Selector de forma de pago (EFECTIVO/TARJETA/TRANSFERENCIA)
- Al confirmar: registrar pedido en localStorage, limpiar carrito, ir a Mis Pedidos

## Admin
- **Dashboard**: total categorías, productos, pedidos, disponibles, pedidos por estado
- **Categorías**: tabla con alta/editar/baja lógica
- **Productos**: tabla con alta/editar/baja + precio, stock, disponible
- **Pedidos**: todos los pedidos de todos los usuarios, filtro por estado, cambiar estado inline

## Datos Demo
- `src/data/productos.ts`: 9 productos con stock, disponible, categoriaId
- `src/data/categorias.ts`: 5 categorías
- Admin seed: `admin@foodstore.local` / `admin123`
