package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Configuration of persistence layer.
 */
@Configuration
public class SpringConfig {

    /**
     * Creates EntityManagerFactory for "prod" and "demo" profiles, based on persistence.xml
     *
     * @return EntityManageFactory
     */
    @Bean
    @Profile({"prod", "demo"})
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("prod");
    }

    /**
     * Creates EntityManagerFactory for "dev" profile, based on persistence.xml
     *
     * @return EntityManageFactory
     */
    @Bean
    @Profile("dev")
    public EntityManagerFactory devEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("dev");
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
}
