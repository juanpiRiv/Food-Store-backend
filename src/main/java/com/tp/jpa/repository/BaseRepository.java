package com.tp.jpa.repository;

import com.tp.jpa.model.Base;
import com.tp.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

// repositorio generico: lo escribo una sola vez y lo heredan todas las entidades
public abstract class BaseRepository<T extends Base> {

    protected final EntityManagerFactory emf;
    // me guardo la clase de la entidad porque en runtime el generico se pierde
    protected final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        // uso siempre la misma factory del singleton
        this.emf = JpaUtil.getEntityManagerFactory();
    }

    // sirve para alta y para modificacion: merge inserta si es nuevo, actualiza si ya existe
    public T guardar(T entity) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();

            // abro la transaccion, guardo y confirmo
            tx.begin();
            T entidadGuardada = em.merge(entity);
            tx.commit();

            return entidadGuardada;
        } catch (RuntimeException e) {
            // si algo falla deshago todo para no dejar la base a medias
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            // pase lo que pase cierro el entity manager
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // busco por id y devuelvo un optional asi el que llama no se come un null
    public Optional<T> buscarPorId(Long id) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            // es solo lectura, no abro transaccion, pero igual cierro
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // traigo solo los que no estan dados de baja (baja logica)
    public List<T> listarActivos() {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            // armo el jpql con el nombre de la entidad, no de la tabla
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

    // baja logica: no borro nada, solo marco el campo eliminado en true
    public boolean eliminarLogico(Long id) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();

            tx.begin();

            T entity = em.find(entityClass, id);

            // si no existe o ya estaba dado de baja no hago nada
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
