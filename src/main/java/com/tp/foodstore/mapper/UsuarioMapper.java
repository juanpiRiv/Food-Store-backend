package com.tp.foodstore.mapper;

import com.tp.foodstore.dto.usuario.UsuarioCreate;
import com.tp.foodstore.dto.usuario.UsuarioDto;
import com.tp.foodstore.dto.usuario.UsuarioEdit;
import com.tp.foodstore.entity.Usuario;
import org.springframework.stereotype.Component;

/**
 * Convierte DTOs de usuarios en la entidad Usuario y viceversa.
 */
@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioCreate dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setMail(dto.getMail());
        usuario.setCelular(dto.getCelular());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol());
        return usuario;
    }

    public UsuarioDto toDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setMail(usuario.getMail());
        dto.setCelular(usuario.getCelular());
        dto.setRol(usuario.getRol());
        return dto;
    }

    public void actualizar(Usuario usuario, UsuarioEdit dto) {
        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            usuario.setApellido(dto.getApellido());
        }
        if (dto.getMail() != null) {
            usuario.setMail(dto.getMail());
        }
        if (dto.getCelular() != null) {
            usuario.setCelular(dto.getCelular());
        }
        if (dto.getContrasena() != null) {
            usuario.setContrasena(dto.getContrasena());
        }
        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }
    }
}
