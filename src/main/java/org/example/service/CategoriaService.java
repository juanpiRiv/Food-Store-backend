package org.example.service;

import org.example.dto.categoria.CategoriaCreate;
import org.example.dto.categoria.CategoriaDto;
import org.example.dto.categoria.CategoriaEdit;
import org.example.model.Categoria;
import org.example.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria crear(CategoriaCreate dto) {
        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        return categoriaRepository.save(categoria);
    }

    public Categoria editar(Long id, CategoriaEdit dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        return categoriaRepository.save(categoria);
    }

    public CategoriaDto buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        return convertirADto(categoria);
    }

    public List<CategoriaDto> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    private CategoriaDto convertirADto(Categoria categoria) {
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}