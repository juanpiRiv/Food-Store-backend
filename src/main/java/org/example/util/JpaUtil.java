package org.example.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("parcial-jpa-pu");

    private JpaUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static void cerrar() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }
}