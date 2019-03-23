package com.epam.esm.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:db.properties")
public class RepositoryTestConfig {
    private final Environment env;

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=jes_test";
    private static final Integer DEFAULT_POOL_SIZE = 30;
    private static final String DEFAULT_SCHEMA = "jes_test";

    public static final String url = "url";
    private static final String user = "user";
    private static final String password = "password";
    private static final String schema = "schema";
    private static final String size = "size";

    public RepositoryTestConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public Properties connectionProperties(){
        Properties properties = new Properties();
        properties.setProperty(user, env.getProperty(user));
        properties.setProperty(password, env.getProperty(password));
        String jdbcUrl = env.getProperty(url, DEFAULT_URL) + "?currentSchema=" + env.getProperty(schema, DEFAULT_SCHEMA);
        properties.setProperty(url, jdbcUrl);
        return properties;
    }
}
