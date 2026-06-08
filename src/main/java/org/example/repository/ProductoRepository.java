package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.model.Producto;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    // consulta propia que pedia el parcial: productos activos de una categoria
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            // navego por los objetos (p.categoria.id) en vez de hacer un join a mano
            String jpql = """
                    SELECT p
                    FROM Producto p
                    WHERE p.categoria.id = :categoriaId
                    AND p.eliminado = false
                    """;

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            // paso el dato con parametro nombrado, mas seguro que concatenar a mano
            query.setParameter("categoriaId", categoriaId);

            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
