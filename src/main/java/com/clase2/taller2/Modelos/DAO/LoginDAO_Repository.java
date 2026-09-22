package com.clase2.taller2.Modelos.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clase2.taller2.Modelos.Entity.Login;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class LoginDAO_Repository implements LoginDAO_Interface {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Login> findAll() {
        return em.createQuery("from Login", Login.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Login> findPendientes() {
        TypedQuery<Login> query = em.createQuery(
            "from Login where estado = com.clase2.taller2.Modelos.Enums.EstadoLogin.PENDIENTE", Login.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Login findByCorreo(String correo) {
        try {
            TypedQuery<Login> query = em.createQuery(
                "from Login where correo = :correo", Login.class);
            query.setParameter("correo", correo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void save(Login login) {
        if (login.getId() != null && login.getId() > 0) {
            em.merge(login);
        } else {
            em.persist(login);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Login findOne(Long id) {
        return em.find(Login.class, id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Login login = findOne(id);
        if (login != null) {
            em.remove(login);
        }
    }

    @Override
    public Login FindByCorreo(String correo) {
        return findByCorreo(correo);
    }
}