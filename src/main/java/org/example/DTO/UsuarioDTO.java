package org.example.DTO;

public record UsuarioDTO(
        Long id,
        boolean eliminado,
        String nombre,
        String apellido,
        String email,
        String celular
) {
}