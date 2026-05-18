package org.example.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestConexion {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("miUnidad");
            em = emf.createEntityManager();

            em.getTransaction().begin();

            Object resultado = em
                    .createNativeQuery("SELECT 1")
                    .getSingleResult();

            em.getTransaction().commit();

            System.out.println("Conexión JPA/Hibernate OK");
            System.out.println("Resultado test SQL: " + resultado);

        } catch (Exception e) {
            System.out.println("Error al conectar con JPA/Hibernate");
            e.printStackTrace();

            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {
            if (em != null) {
                em.close();
            }

            if (emf != null) {
                emf.close();
            }
        }
    }
}