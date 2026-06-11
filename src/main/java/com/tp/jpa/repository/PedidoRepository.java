package com.tp.jpa.repository;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.enums.Estado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PedidoRepository extends BaseRepository<Pedido> {

    public PedidoRepository() {
        super(Pedido.class);
    }

    // obtiene los pedidos activos de un usuario especifico navegando por la coleccion
    public List<Pedido> buscarPorUsuario(Long idUsuario) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            // navego desde Usuario hacia sus pedidos usando la coleccion mapeada
            String jpql = """
                    SELECT p FROM Usuario u
                    JOIN u.pedidos p
                    WHERE u.id = :uid
                    AND p.eliminado = false
                    """;

            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
            query.setParameter("uid", idUsuario);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // obtiene pedidos activos filtrados por estado
    public List<Pedido> buscarPorEstado(Estado estado) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            // filtro directo por el enum Estado en la tabla pedidos
            String jpql = """
                    SELECT p FROM Pedido p
                    WHERE p.estado = :estado
                    AND p.eliminado = false
                    """;

            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
            query.setParameter("estado", estado);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
