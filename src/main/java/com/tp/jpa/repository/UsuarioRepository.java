package com.tp.jpa.repository;

import com.tp.jpa.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    // busca un usuario activo por su mail unico
    public Optional<Usuario> buscarPorMail(String mail) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            // filtra por mail y excluye bajas logicas
            String jpql = """
                    SELECT u FROM Usuario u
                    WHERE u.mail = :mail
                    AND u.eliminado = false
                    """;

            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("mail", mail);

            List<Usuario> resultados = query.getResultList();
            return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
