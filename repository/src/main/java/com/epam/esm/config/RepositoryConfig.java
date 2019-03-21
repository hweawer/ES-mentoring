package com.epam.esm.config;

import com.epam.esm.pool.ConnectionPool;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.epam.esm")
@PropertySource(value = "classpath:database.properties")
public class RepositoryConfig {
    private final Environment env;

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=jes_test";
    private static final Integer DEFAULT_POOL_SIZE = 30;
    private static final String DEFAULT_SCHEMA = "jes_test";

    public static final String url = "url";
    private static final String user = "user";
    private static final String password = "password";
    private static final String schema = "schema";
    private static final String size = "size";

    public RepositoryConfig(Environment env) {
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

    @Bean
    public DataSource connectionPool(){
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.setSize(env.getProperty(size, Integer.class, DEFAULT_POOL_SIZE));
        connectionPool.setConnectionProperties(connectionProperties());
        return connectionPool;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
