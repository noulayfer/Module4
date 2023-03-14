package com.fedorenko.config;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class JPAConfig {
    private static EntityManagerFactory entityManagerFactory;

    private JPAConfig() {
    }

    public static EntityManager getEntityManager() {
        return Optional.ofNullable(entityManagerFactory)
                .or(() -> {
                    entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
                    return Optional.of(entityManagerFactory);
                })
                .map(EntityManagerFactory::createEntityManager)
                .orElseThrow(IllegalArgumentException::new);
    }
}
