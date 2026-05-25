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
public class UsuarioEdit {

    private String nombre;
    private String apellido;
    private String celular;
    private String contrasenia;
    private Rol rol;
}