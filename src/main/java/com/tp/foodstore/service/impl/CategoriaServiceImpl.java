package com.tp.foodstore.service.impl;

import com.tp.foodstore.dto.categoria.CategoriaCreate;
import com.tp.foodstore.dto.categoria.CategoriaDto;
import com.tp.foodstore.dto.categoria.CategoriaEdit;
import com.tp.foodstore.entity.Categoria;
import com.tp.foodstore.exception.NegocioException;
import com.tp.foodstore.mapper.CategoriaMapper;
import com.tp.foodstore.repository.CategoriaRepository;
import com.tp.foodstore.service.interfaces.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de categorías.
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public CategoriaDto crear(CategoriaCreate dto) {
        Categoria categoria = categoriaRepository.save(categoriaMapper.toEntity(dto));
        return categoriaMapper.toDto(categoria);
    }

    @Override
    public CategoriaDto obtenerPorId(Long id) {
        return categoriaMapper.toDto(buscar(id));
    }

    @Override
    public List<CategoriaDto> listar() {
        return categoriaRepository.findAll().stream()
                .filter(categoria -> !categoria.isEliminado())
                .map(categoriaMapper::toDto)
                .toList();
    }

    @Override
    public CategoriaDto actualizar(Long id, CategoriaEdit dto) {
        Categoria categoria = buscar(id);
        categoriaMapper.actualizar(categoria, dto);
        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Override
    public void eliminar(Long id) {
        Categoria categoria = buscar(id);
        categoria.setEliminado(true);
        categoriaRepository.save(categoria);
    }

    private Categoria buscar(Long id) {
        return categoriaRepository.findById(id)
                .filter(categoria -> !categoria.isEliminado())
                .orElseThrow(() -> new NegocioException("Categoría no encontrada con id " + id));
    }
}
