package com.epam.esm.repository.config;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.epam.esm")
@PropertySource(value = "classpath:database.properties")
public class RepositoryConfig {
    private static Logger logger = LogManager.getLogger();
    private final Environment env;

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=jes_dev";
    private static final Integer DEFAULT_POOL_SIZE = 30;
    private static final String DEFAULT_SCHEMA = "jes_dev";

    public static final String url = "url";
    private static final String user = "user";
    private static final String password = "password";
    private static final String schema = "schema";
    private static final String size = "size";

    public static String schemaInUse;

    public RepositoryConfig(Environment env) {
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

    @Bean
    public DataSource connectionPool() {
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.setSize(env.getProperty(size, Integer.class, DEFAULT_POOL_SIZE));
        connectionPool.setConnectionProperties(connectionProperties());
        return connectionPool;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public BeanPropertyRowMapper<GiftCertificate> giftPropertyMapper(){
        return new BeanPropertyRowMapper<>(GiftCertificate.class);
    }

    @Bean
    public BeanPropertyRowMapper<Tag> tagPropertyMapper(){
        return new BeanPropertyRowMapper<>(Tag.class);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
