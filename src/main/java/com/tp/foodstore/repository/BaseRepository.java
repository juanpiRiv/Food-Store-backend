package com.tp.foodstore.repository;

import com.tp.foodstore.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio base genérico: lo escribo una sola vez y lo heredan todas las entidades.
 */
@Repository
public interface BaseRepository<T extends Base, ID> extends JpaRepository<T, ID> {
}
