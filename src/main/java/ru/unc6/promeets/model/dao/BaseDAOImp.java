package ru.unc6.promeets.model.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Vladimir on 06.02.2016.
 * @param <T>
 */

@Transactional
public class BaseDAOImp<T> implements BaseDAO<T> {


    protected EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T getById(Class<T> entity, long id) {
        return entityManager.find(entity, id);
    }

    @Override
    public void create(T obj) {
        entityManager.persist(obj);
    }

    @Override
    public T update(T obj) {
        return entityManager.merge(obj);
    }

    @Override
    public void delete(Class<T> entity, long id) {
        entityManager.remove(entityManager.find(entity, id));
    }


    @Override
    public List<T> getAll(Class<T> entity) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entity);
        Root<T> rootEntry = cq.from(entity);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

}
