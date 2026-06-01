# Parcial 2 - JPA

## Descripción

Aplicación de consola desarrollada en Java con JPA/Hibernate que permite gestionar categorías y productos de un Food Store mediante operaciones ABM completas y una consulta JPQL personalizada para filtrar productos por categoría.

## Funcionalidades

- ABM completo de categorías (alta, baja lógica, modificación, listado)
- ABM completo de productos con asociación a categoría (alta, baja lógica, modificación, listado)
- Consulta JPQL de productos activos filtrados por categoría
- Baja lógica: los registros no se eliminan físicamente de la base de datos

## Estructura del proyecto

```
src/main/java/org/example/
├── model/
│   ├── Base.java
│   ├── Categoria.java
│   └── Producto.java
├── repository/
│   ├── BaseRepository.java
│   ├── CategoriaRepository.java
│   └── ProductoRepository.java
├── util/
│   └── JpaUtil.java
└── Main.java
```

## Tecnologías

- Java 21
- Gradle
- JPA / Hibernate
- H2 Database (embebida)
- Lombok

## Ejecución

Compilar el proyecto:

```bash
./gradlew clean build
```

Correr:

```bash
./gradlew run
```

El menú de consola se inicia automáticamente. Seguir las opciones numeradas para navegar entre Categorías, Productos y Reportes.
