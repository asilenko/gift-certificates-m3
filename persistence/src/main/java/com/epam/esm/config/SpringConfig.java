package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

/**
 * Configuration of persistence layer.
 */
@Configuration
@PropertySource(value = "classpath:db.properties")
public class SpringConfig {

    @Autowired
    private Environment env;

    /**
     * Configuration of data source for prod profile.
     *
     * @return DataSource
     */
    @Bean
    @Profile("prod")
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("db_driver"));
        dataSourceBuilder.url(env.getProperty("db_url"));
        dataSourceBuilder.username(env.getProperty("db_username"));
        dataSourceBuilder.password(env.getProperty("db_password"));
        return dataSourceBuilder.build();
    }

    /**
     * Configuration of data source for dev profile.
     *
     * @return DataSource
     */
    @Bean
    @Profile("dev")
    public DataSource createH2DaraSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("gift-certificates-empty-tables.sql")
                .build();
    }

    /**
     * Creates EntityManagerFactory for "prod" profile, based on persistence.xml
     *
     * @return EntityManageFactory
     */
    @Bean
    @Profile("prod")
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

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
