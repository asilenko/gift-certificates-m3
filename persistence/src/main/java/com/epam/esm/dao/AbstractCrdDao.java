package com.epam.esm.dao;

import com.epam.esm.dao.jpa.CrdDao;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * Provides implementation for CRD methods for entities.
 */
public abstract class AbstractCrdDao<T extends Serializable> implements CrdDao<T> {
    public Class<T> clazz;

    @Autowired
    public EntityManager entityManager;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * {@inheritDoc}
     */
    public Optional<T> findById(final Long id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    /**
     * {@inheritDoc}
     */
    public Collection<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public T create(T entityModel) {
        entityManager.getTransaction().begin();
        entityManager.persist(entityModel);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return entityModel;
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<T> entityModel = findById(id);
        if (entityModel.isEmpty()) {
            throw new ResourceNotFoundException("No entity related to id: " + id);
        } else {
            entityManager.getTransaction().begin();
            entityManager.remove(entityModel.get());
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
    }
}
