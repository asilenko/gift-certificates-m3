package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Configuration of persistence layer.
 */
@Configuration
@PropertySource(value = "classpath:db.properties")
@ComponentScan("com.epam.esm")
public class SpringJDBCConfig {

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

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsertTag(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("tags").usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsertCertificate(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("GiftCertificates").usingGeneratedKeyColumns("id");
    }
}
