package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.model.Producto;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            /*
             * Consulta JPQL que obtiene productos activos pertenecientes
             * a una categoria especifica usando parametro nombrado.
             */
            String jpql = """
                    SELECT p
                    FROM Producto p
                    WHERE p.categoria.id = :categoriaId
                    AND p.eliminado = false
                    """;

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("categoriaId", categoriaId);

            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}