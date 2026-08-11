package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Usuario;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de la entidad Usuario.
 */
@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
}
