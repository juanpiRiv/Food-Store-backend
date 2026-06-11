# Food Store — TPI Programación III

**Trabajo Práctico Integrador — UTN**
**Alumno**: Juan Pablo Rivero

---

## Descripción

Food Store es un sistema de ecommerce de comida compuesto por:
- **Backend de consola** (Java + Gradle + JPA/Hibernate + H2)
- **Frontend web** (TypeScript + Vite + localStorage)

Los dos componentes son independientes: el backend persiste datos en una base H2, el frontend usa localStorage para la demo académica.

---

## Tecnologías

### Backend
| Tecnología | Versión |
|---|---|
| Java | 21 |
| Gradle | 9.5.1 |
| Hibernate ORM | 6.6.4 |
| H2 Database | 2.3.232 |
| Lombok | 1.18.44 |

### Frontend
| Tecnología | Versión |
|---|---|
| TypeScript | 5.7 |
| Vite | 6.x |
| HTML/CSS Puro | — |

---

## Estructura

```
Food-Store-backend/
├── build.gradle
├── settings.gradle
├── src/main/java/com/tp/jpa/
│   ├── Main.java
│   ├── model/
│   │   ├── Base.java
│   │   ├── Calculable.java
│   │   ├── Categoria.java, Producto.java
│   │   ├── Usuario.java, Pedido.java, DetallePedido.java
│   │   └── enums/ (Rol, Estado, FormaPago)
│   ├── repository/
│   └── util/JpaUtil.java
├── src/main/resources/META-INF/persistence.xml
├── specs/
└── docs/
```

---

## Cómo correr el Backend

```bash
# macOS/Linux
./gradlew run

# Windows
gradlew.bat run
```

La base de datos H2 se crea automáticamente en `./data/jpa_db`.

---

## Cómo correr el Frontend

```bash
cd Food-Store-Prog-III-JuanPabloRivero-UTN
npm install
npm run dev
# Abrir http://localhost:5173
```

---

## Usuarios de Prueba (Frontend)

| Email | Contraseña | Rol |
|---|---|---|
| admin@foodstore.local | admin123 | ADMIN |
| (registrarse) | mínimo 8 caracteres | USUARIO |

---

## Funcionalidades

### Backend (Consola)
- ABM Categorías, Productos, Usuarios, Pedidos
- Alta de pedido atómica (una transacción, descuenta stock)
- Cambiar estado de pedido
- Baja lógica en todas las entidades
- 4 tipos de reportes (incluyendo total facturado solo TERMINADO)

### Frontend (Web)
- Login con roles (ADMIN / USUARIO)
- Catálogo con búsqueda, filtro por categoría y ordenamiento
- Detalle de producto con selector de cantidad y validación de stock
- Carrito con forma de pago y revalidación de stock al confirmar
- Mis pedidos con estado visible
- Panel Admin: Dashboard, Categorías, Productos, Pedidos

---

## Seguridad (Aclaración Académica)

La autenticación del frontend es **solo educativa**:
- No usa JWT ni sesiones del lado servidor.
- Los datos se guardan en localStorage del navegador.
- **No apto para producción real.**

---

## Build

```bash
# Backend
./gradlew clean build   # → BUILD SUCCESSFUL

# Frontend
npm run build           # → sin errores TypeScript
```

---

## Video Demo

[Link al video — pendiente de grabación]

---

## Branch

`tpi-final-foodstore` (no se modificó master directamente)
