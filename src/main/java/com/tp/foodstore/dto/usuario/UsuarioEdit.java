package com.tp.foodstore.dto.usuario;

import com.tp.foodstore.entity.enums.Rol;
import lombok.Builder;
import lombok.Data;

/**
 * DTO para editar un usuario.
 */
@Data
@Builder
public class UsuarioEdit {

    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
}
