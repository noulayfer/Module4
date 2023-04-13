package com.fedorenko.repository;

import com.fedorenko.config.JPAConfig;
import com.fedorenko.model.Detail;

import javax.persistence.EntityManager;
import java.util.List;

public class DetailRepository {
    private static DetailRepository detailRepository;

    private DetailRepository(){
    }

    public static DetailRepository getInstance() {
        if (detailRepository == null) {
            detailRepository = new DetailRepository();
        }
        return detailRepository;
    }

    public Detail save(final Detail detail) {
        final EntityManager entityManager = JPAConfig.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(detail);
        entityManager.getTransaction().commit();
        entityManager.close();
        return detail;
    }

    public Detail findById(final String id) {
        final EntityManager entityManager = JPAConfig.getEntityManager();
        return entityManager.find(Detail.class, id);
    }

    public List<Detail> getAll() {
        final EntityManager entityManager = JPAConfig.getEntityManager();
        return entityManager.createQuery("from " + Detail.class.getSimpleName(), Detail.class)
                .getResultList();
    }

    public void delete(final String id) {
        final EntityManager entityManager = JPAConfig.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from " + Detail.class.getSimpleName() + " where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
