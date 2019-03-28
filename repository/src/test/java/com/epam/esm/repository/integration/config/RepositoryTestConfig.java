package com.epam.esm.repository.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:init.sql")
public class RepositoryTestConfig {
    private final Environment env;

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=jes_test";
    private static final String DEFAULT_SCHEMA = "jes_test";

    public static final String url = "url";
    private static final String user = "user";
    private static final String password = "password";
    private static final String schema = "schema";

    public static String schemaInUse;

    public RepositoryTestConfig(Environment env) {
        this.env = env;
    }
    @Bean
    public Properties connectionProperties(){
        Properties properties = new Properties();
        properties.setProperty(user, env.getProperty(user));
        properties.setProperty(password, env.getProperty(password));
        schemaInUse = env.getProperty(schema, DEFAULT_SCHEMA);
        String jdbcUrl = env.getProperty(url, DEFAULT_URL) + "?currentSchema=" + schemaInUse;
        properties.setProperty(url, jdbcUrl);
        return properties;
    }
}
