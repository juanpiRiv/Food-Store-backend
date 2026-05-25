package org.example.service;

import org.example.dto.producto.ProductoCreate;
import org.example.dto.producto.ProductoDto;
import org.example.dto.producto.ProductoEdit;
import org.example.model.Categoria;
import org.example.model.Producto;
import org.example.repository.CategoriaRepository;
import org.example.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Producto crear(ProductoCreate dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .descripcion(dto.getDescripcion())
                .stock(dto.getStock())
                .imagen(dto.getImagen())
                .disponible(dto.isDisponible())
                .build();

        categoria.agregarProducto(producto);

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto editar(Long id, ProductoEdit dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setImagen(dto.getImagen());
        producto.setDisponible(dto.isDisponible());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    public ProductoDto buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return convertirADto(producto);
    }

    public List<ProductoDto> listar() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public ProductoDto convertirADto(Producto producto) {
        Long categoriaId = null;
        String categoriaNombre = null;

        if (producto.getCategoria() != null) {
            categoriaId = producto.getCategoria().getId();
            categoriaNombre = producto.getCategoria().getNombre();
        }

        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                producto.getStock(),
                producto.getImagen(),
                producto.isDisponible(),
                categoriaId,
                categoriaNombre
        );
    }
}