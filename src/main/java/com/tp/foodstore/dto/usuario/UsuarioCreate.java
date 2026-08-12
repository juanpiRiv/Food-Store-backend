package com.tp.foodstore.dto.usuario;

import com.tp.foodstore.entity.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * DTO para crear un usuario.
 */
@Data
@Builder
public class UsuarioCreate {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El mail es obligatorio")
    @Email(message = "El mail debe tener un formato válido")
    private String mail;

    private String celular;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    private Rol rol;
}
