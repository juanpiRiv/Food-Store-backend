# Parcial 2 - JPA

## Descripción
Aplicación de consola desarrollada en Java con JPA para gestionar categorías y productos mediante operaciones ABM y una consulta JPQL personalizada.

## Funcionalidades
- Alta, baja lógica, modificación y listado de categorías.
- Alta, baja lógica, modificación y listado de productos.
- Consulta de productos activos por categoría usando JPQL.

## Tecnologías
- Java 21
- Maven
- JPA
- Hibernate
- H2 Database
- Lombok

## Ejecución

Compilar el proyecto:

mvn clean compile

Ejecutar la aplicación:

mvn exec:java

## Estructura principal
- org.example.Main: menú de consola.
- org.example.repository.BaseRepository: repositorio genérico con operaciones CRUD.
- org.example.repository.CategoriaRepository: repositorio específico de categorías.
- org.example.repository.ProductoRepository: repositorio específico de productos y consulta JPQL por categoría.
- org.example.util.JpaUtil: utilidad para crear y cerrar el EntityManagerFactory.
- src/main/resources/META-INF/persistence.xml: configuración de JPA e Hibernate.

## Nota
El proyecto base estaba organizado bajo el paquete org.example. Para mantener compatibilidad con las entidades existentes, la implementación del parcial se realizó sobre esa estructura, reemplazando Spring Boot por JPA puro de consola.

## Video
Link al video de presentación:
