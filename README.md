# Food Store — Backend Spring Boot

**Trabajo Final Integrador — Programación III — UTN TUPaD**
**Alumno:** Juan Pablo Rivero

---

## Descripción

Sistema de gestión de pedidos de comida **Food Store** en Spring Boot. La API REST se desarrollará en próximas etapas sobre una arquitectura en capas (controladores, servicios, repositorios). Continúa la práctica JPA anterior: el dominio (categorías, productos, usuarios, pedidos) se reutilizará.

---

## Tecnologías

| Tecnología       | Versión   |
|------------------|-----------|
| Java             | 21        |
| Maven            | 3.9       |
| Spring Boot      | 3.5.9     |
| Spring Web       | -         |
| Spring Data JPA  | -         |
| Lombok           | -         |
| H2 Database      | -         |
| Spring Boot DevTools | -    |

---

## Instalación y ejecución

```bash
# macOS / Linux
./mvnw spring-boot:run

# alternativa (si Maven está instalado globalmente)
mvn spring-boot:run
```

La consola de H2 queda disponible en [http://localhost:8080/h2-console](http://localhost:8080/h2-console) con:

- **JDBC URL:** `jdbc:h2:mem:foodstore`
- **Usuario:** `sa`
- **Contraseña:** (vacía)

Al iniciar la aplicación se imprime en consola un resumen de los datos de ejemplo instanciados en memoria a partir de los DTOs (usuarios, categorías, productos y pedidos con sus totales).

---

## Estructura de paquetes

```
src/main/java/com/tp/foodstore/
├── FoodStoreApplication.java     ← clase principal
├── config/                       ← configuración de la aplicación (incluye DataInitializer)
├── controller/                   ← controladores REST
├── service/
│   ├── interfaces/               ← contratos de servicios
│   └── impl/                     ← implementaciones de servicios
├── repository/                   ← repositorios Spring Data JPA
├── entity/                       ← entidades JPA reutilizadas de la práctica JPA anterior
│   └── enums/                    ← enums del dominio (Estado, FormaPago, Rol)
├── dto/
│   ├── categoria/                ← DTOs de categorías
│   ├── detallePedido/            ← DTOs de detalle de pedidos
│   ├── pedido/                   ← DTOs de pedidos
│   ├── producto/                 ← DTOs de productos
│   └── usuario/                  ← DTOs de usuarios
├── mapper/                       ← conversión entre entidades y DTOs
├── exception/                    ← excepciones y manejo global de errores
└── util/                         ← clases de utilidad
```

---

## Responsabilidad de cada paquete

| Paquete      | Responsabilidad |
|--------------|-----------------|
| config       | Beans y configuración general de la aplicación; `DataInitializer` instancia datos de ejemplo a partir de DTOs al iniciar |
| controller   | Expone los endpoints de la API REST |
| service      | Contratos e implementaciones de la lógica de negocio |
| repository   | Acceso a datos con Spring Data JPA |
| entity       | Entidades JPA del dominio reutilizadas de la práctica anterior, junto con sus enums en `entity/enums/` |
| dto          | Objetos de transferencia de datos agrupados por módulo |
| mapper       | Conversión entre entidades y DTOs |
| exception    | Excepciones de negocio y manejo global de errores |
| util         | Utilidades de propósito general |
