package org.example.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Rol;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreate {

    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String contrasenia;
    private Rol rol;
}