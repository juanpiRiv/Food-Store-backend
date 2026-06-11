# Fuente de Verdad — Consigna TPI Food Store (Programación III)

## Descripción General

Food Store es un sistema de ecommerce de comida dividido en dos componentes independientes:

1. **Backend de consola**: Java + Gradle + JPA/Hibernate + H2 (archivo).
2. **Frontend web**: TypeScript + Vite + HTML/CSS puro + localStorage.

## Tecnologías

### Backend
- Java 21
- Gradle (sin Spring Boot, sin Maven)
- JPA/Hibernate 6.x
- H2 Database en modo archivo (`./data/jpa_db`)
- Lombok (reducción de boilerplate)
- Sin API REST — solo aplicación de consola

### Frontend
- TypeScript + Vite
- HTML/CSS puro (sin frameworks de UI)
- localStorage para datos y sesión
- JSON/TypeScript para datos de demo
- Autenticación educativa (no seguridad real)

## Roles
- **ADMIN**: gestiona categorías, productos, usuarios, pedidos y reportes.
- **USUARIO**: navega el catálogo, agrega al carrito, realiza pedidos.

## Reglas de la Consigna
- No API REST para el backend final.
- No Spring Boot como entrega.
- No Maven como entrega.
- Backend funciona por menú de consola con persistencia JPA/H2.
- Frontend autónomo (no conecta al backend).
- Autenticación frontend solo educativa (localStorage, sin JWT).
