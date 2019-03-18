package com.epam.esm.config;

import com.epam.esm.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.epam.esm")
@PropertySource(value = "classpath:pool.properties")
public class AppConfig {
    @Autowired
    private Environment env;

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final Integer DEFAULT_POOL_SIZE = 30;

    public static final String url = "url";
    private static final String user = "user";
    private static final String password = "password";
    private static final String size = "size";

    @Bean
    public Properties connectionProperties(){
        Properties properties = new Properties();
        properties.setProperty(user, env.getProperty(user));
        properties.setProperty(password, env.getProperty(password));
        properties.setProperty(url, env.getProperty(url, DEFAULT_URL));
        return properties;
    }

    @Bean
    public DataSource connectionPool(){
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.setSize(env.getProperty(size, Integer.class, DEFAULT_POOL_SIZE));
        connectionPool.setConnectionProperties(connectionProperties());
        return connectionPool;
    }
}
