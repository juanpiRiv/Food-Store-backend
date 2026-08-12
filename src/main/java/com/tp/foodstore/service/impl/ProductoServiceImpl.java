package com.tp.foodstore.service.impl;

import com.tp.foodstore.dto.producto.ProductoCreate;
import com.tp.foodstore.dto.producto.ProductoDto;
import com.tp.foodstore.dto.producto.ProductoEdit;
import com.tp.foodstore.entity.Categoria;
import com.tp.foodstore.entity.Producto;
import com.tp.foodstore.exception.NegocioException;
import com.tp.foodstore.mapper.ProductoMapper;
import com.tp.foodstore.repository.CategoriaRepository;
import com.tp.foodstore.repository.ProductoRepository;
import com.tp.foodstore.service.interfaces.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de productos.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository,
                               ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public ProductoDto crear(ProductoCreate dto) {
        Categoria categoria = buscarCategoria(dto.getCategoriaId());
        Producto producto = productoRepository.save(productoMapper.toEntity(dto, categoria));
        return productoMapper.toDto(producto);
    }

    @Override
    public ProductoDto obtenerPorId(Long id) {
        return productoMapper.toDto(buscar(id));
    }

    @Override
    public List<ProductoDto> listar() {
        return productoRepository.findAll().stream()
                .filter(producto -> !producto.isEliminado())
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoDto actualizar(Long id, ProductoEdit dto) {
        Producto producto = buscar(id);
        Categoria categoria = buscarCategoria(dto.getCategoriaId());
        productoMapper.actualizar(producto, dto, categoria);
        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = buscar(id);
        producto.setEliminado(true);
        productoRepository.save(producto);
    }

    private Producto buscar(Long id) {
        return productoRepository.findById(id)
                .filter(producto -> !producto.isEliminado())
                .orElseThrow(() -> new NegocioException("Producto no encontrado con id " + id));
    }

    private Categoria buscarCategoria(Long id) {
        return categoriaRepository.findById(id)
                .filter(categoria -> !categoria.isEliminado())
                .orElseThrow(() -> new NegocioException("Categoría no encontrada con id " + id));
    }
}
