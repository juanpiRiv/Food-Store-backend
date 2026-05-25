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
public class UsuarioDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private Rol rol;
    private int cantidadPedidos;
}