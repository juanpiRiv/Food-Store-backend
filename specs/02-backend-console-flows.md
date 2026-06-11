# Backend — Flujos de Consola

## Menú Principal

```
===== FOOD STORE - MENU PRINCIPAL =====
1. Gestionar Categorias
2. Gestionar Productos
3. Gestionar Usuarios
4. Gestionar Pedidos
5. Reportes
0. Salir
```

## Submenú Categorías
1. Alta (nombre obligatorio)
2. Modificar (Enter conserva valor anterior)
3. Baja lógica
4. Listado (solo activos)

## Submenú Productos
1. Alta (requiere categoría activa, precio > 0, stock >= 0)
2. Modificar (Enter conserva valor anterior)
3. Baja lógica
4. Listado (con categoría)

## Submenú Usuarios
1. Alta (mail único validado)
2. Modificar (Enter conserva valor anterior)
3. Baja lógica
4. Listado
5. Buscar por mail (no muestra contraseña)

## Submenú Pedidos
1. **Alta (atómica)**:
   - Listar usuarios activos → seleccionar
   - Seleccionar forma de pago
   - Loop: agregar ítems (producto + cantidad)
   - Validar: disponible, stock suficiente
   - Si no hay ítems → cancelar
   - Única transacción JPA: em.find + em.persist + commit
   - Si falla → rollback
2. Cambiar estado (PENDIENTE/CONFIRMADO/TERMINADO/CANCELADO)
3. Baja lógica
4. Listado
5. Pedidos por usuario
6. Pedidos por estado

## Submenú Reportes
1. Productos por categoría
2. Pedidos por usuario (con total gastado)
3. Pedidos por estado (resumen + detalle)
4. **Total facturado** — solo pedidos con estado TERMINADO, mostrar con 2 decimales

## Reglas de Input
- Nunca crashea con `InputMismatchException` — usa `sc.nextLine()` + try/catch
- Enter vacío conserva el valor anterior en modificaciones
- Validaciones antes de persistir (precio > 0, stock >= 0, mail único)
