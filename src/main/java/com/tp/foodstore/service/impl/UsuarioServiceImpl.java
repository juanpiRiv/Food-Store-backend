package com.tp.foodstore.service.impl;

import com.tp.foodstore.dto.usuario.UsuarioCreate;
import com.tp.foodstore.dto.usuario.UsuarioDto;
import com.tp.foodstore.entity.Usuario;
import com.tp.foodstore.exception.NegocioException;
import com.tp.foodstore.mapper.UsuarioMapper;
import com.tp.foodstore.repository.UsuarioRepository;
import com.tp.foodstore.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de usuarios.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioDto crear(UsuarioCreate dto) {
        Usuario usuario = usuarioRepository.save(usuarioMapper.toEntity(dto));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto obtenerPorId(Long id) {
        return usuarioMapper.toDto(buscar(id));
    }

    @Override
    public UsuarioDto buscarPorMail(String mail) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> !usuario.isEliminado())
                .filter(usuario -> usuario.getMail().equalsIgnoreCase(mail))
                .map(usuarioMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new NegocioException("Usuario no encontrado con mail " + mail));
    }

    @Override
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> !usuario.isEliminado())
                .map(usuarioMapper::toDto)
                .toList();
    }

    private Usuario buscar(Long id) {
        return usuarioRepository.findById(id)
                .filter(usuario -> !usuario.isEliminado())
                .orElseThrow(() -> new NegocioException("Usuario no encontrado con id " + id));
    }
}
