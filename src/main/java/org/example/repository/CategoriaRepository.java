package org.example.repository;

import org.example.model.Categoria;

// no escribo nada propio: heredo alta, baja, busqueda y listado del base
public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        // le paso la clase al padre para que sepa con que entidad trabajar
        super(Categoria.class);
    }
}
