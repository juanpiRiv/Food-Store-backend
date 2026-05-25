package org.example.repository;

import org.example.model.Categoria;

public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }
}