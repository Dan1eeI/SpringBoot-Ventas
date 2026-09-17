package com.clase2.taller2.Modelos.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clase2.taller2.Modelos.Entity.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Esta clase sirve para la persistencia de datos y la interacción con la base de datos
@Repository
public class ProductoDAO_Repository implements ProductoDAO_Interface {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Producto> findAll() {
        return em.createQuery("from Producto").getResultList();
    }

    @Transactional
    @Override
    public void save(Producto producto) {
        if (producto.getId() != null && producto.getId() > 0) {
            em.merge(producto);
        } else {
            em.persist(producto);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findOne(Long id) {
        return em.find(Producto.class, id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Producto producto = findOne(id);
        if (producto != null) {
            em.remove(producto);
        }
    }
}
