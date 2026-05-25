package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.model.Base;
import org.example.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends Base> {

    protected final EntityManagerFactory emf;
    protected final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.emf = JpaUtil.getEntityManagerFactory();
    }

    public T guardar(T entity) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();

            tx.begin();
            T entidadGuardada = em.merge(entity);
            tx.commit();

            return entidadGuardada;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Optional<T> buscarPorId(Long id) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<T> listarActivos() {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            String jpql = "SELECT e FROM " + entityClass.getSimpleName()
                    + " e WHERE e.eliminado = false";

            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean eliminarLogico(Long id) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();

            tx.begin();

            T entity = em.find(entityClass, id);

            if (entity == null || entity.isEliminado()) {
                tx.rollback();
                return false;
            }

            entity.setEliminado(true);
            em.merge(entity);

            tx.commit();
            return true;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}