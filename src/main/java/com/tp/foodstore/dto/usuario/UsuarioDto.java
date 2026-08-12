package com.tp.foodstore.dto.usuario;

import com.tp.foodstore.entity.enums.Rol;
import lombok.Data;

/**
 * DTO que representa un usuario.
 */
@Data
public class UsuarioDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private Rol rol;
}
