package com.tp.foodstore.service.interfaces;

import com.tp.foodstore.dto.usuario.UsuarioCreate;
import com.tp.foodstore.dto.usuario.UsuarioDto;

import java.util.List;

/**
 * Servicio de usuarios: crea y consulta usuarios.
 */
public interface UsuarioService {

    UsuarioDto crear(UsuarioCreate dto);

    UsuarioDto obtenerPorId(Long id);

    UsuarioDto buscarPorMail(String mail);

    List<UsuarioDto> listar();
}
