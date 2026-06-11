# Backend — Especificación de Dominio

## Clase Base (@MappedSuperclass)

```java
@MappedSuperclass
public abstract class Base {
    @Id @GeneratedValue(strategy = IDENTITY) protected Long id;
    protected boolean eliminado;       // baja lógica
    protected LocalDateTime createdAt; // seteado en @PrePersist
}
```

## Interface Calculable

```java
public interface Calculable {
    void calcularTotal();
}
```

## Enums

| Enum       | Valores                                        |
|------------|------------------------------------------------|
| Rol        | ADMIN, USUARIO                                 |
| Estado     | PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO    |
| FormaPago  | TARJETA, TRANSFERENCIA, EFECTIVO               |

## Entidades

### Categoria
- `nombre: String`
- `descripcion: String`
- `productos: Set<Producto>` — @OneToMany mappedBy="categoria"

### Producto
- `nombre: String`
- `precio: Double`
- `descripcion: String`
- `stock: int`
- `imagen: String`
- `disponible: boolean`
- `categoria: Categoria` — @ManyToOne @JoinColumn(name="categoria_id")

### Usuario
- `nombre: String`
- `apellido: String`
- `mail: String` — @Column(unique=true)
- `celular: String`
- `contrasena: String`
- `rol: Rol`
- `pedidos: Set<Pedido>` — @OneToMany cascade=ALL

### Pedido (implements Calculable)
- `fecha: LocalDateTime`
- `estado: Estado`
- `total: Double`
- `formaPago: FormaPago`
- `detalles: Set<DetallePedido>` — @OneToMany cascade=ALL orphanRemoval=true
- `addDetallePedido(int cantidad, Producto producto)`: crea DetallePedido y lo agrega
- `calcularTotal()`: suma subtotales de todos los detalles

### DetallePedido
- `cantidad: int`
- `subtotal: Double`
- `producto: Producto` — @ManyToOne @JoinColumn(name="producto_id")

## Repositorios

| Repositorio         | Métodos clave                              |
|---------------------|---------------------------------------------|
| BaseRepository\<T>  | guardar, buscarPorId, listarActivos, eliminarLogico |
| CategoriaRepository | hereda de Base                             |
| ProductoRepository  | buscarPorCategoria(Long categoriaId)       |
| UsuarioRepository   | buscarPorMail(String mail)                 |
| PedidoRepository    | buscarPorUsuario(Long id), buscarPorEstado(Estado) |

## Package
`com.tp.jpa`
