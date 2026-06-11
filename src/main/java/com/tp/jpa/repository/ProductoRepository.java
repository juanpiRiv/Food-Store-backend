package com.tp.jpa.repository;

import com.tp.jpa.model.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    // consulta de la consigna: productos activos de una categoria especifica
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            // navego desde Categoria hacia sus productos usando la coleccion mapeada
            String jpql = """
                    SELECT p FROM Categoria c
                    JOIN c.productos p
                    WHERE c.id = :catId
                    AND p.eliminado = false
                    """;

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("catId", categoriaId);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
