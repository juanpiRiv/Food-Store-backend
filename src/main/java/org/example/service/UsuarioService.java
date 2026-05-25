package org.example.service;

import org.example.dto.usuario.UsuarioCreate;
import org.example.dto.usuario.UsuarioDto;
import org.example.dto.usuario.UsuarioEdit;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario crear(UsuarioCreate dto) {
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .celular(dto.getCelular())
                .contraseña(dto.getContrasenia())
                .rol(dto.getRol())
                .build();

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario editar(Long id, UsuarioEdit dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCelular(dto.getCelular());
        usuario.setContraseña(dto.getContrasenia());
        usuario.setRol(dto.getRol());

        return usuarioRepository.save(usuario);
    }

    public UsuarioDto buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return convertirADto(usuario);
    }

    public UsuarioDto buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return convertirADto(usuario);
    }

    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDto convertirADto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getRol(),
                usuario.cantidadPedidos()
        );
    }
}