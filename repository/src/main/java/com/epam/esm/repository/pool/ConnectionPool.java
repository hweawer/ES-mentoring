package com.epam.esm.repository.pool;

import com.epam.esm.repository.config.RepositoryConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public final class ConnectionPool extends AbstractDriverBasedDataSource implements Closeable {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final String CLOSE_METHOD = "close";
    private static final String FORCE_CLOSE = "forceClose";

    private Integer size;

    private BlockingQueue<ProxyConnection> availableConnections;
    private Set<ProxyConnection> usedConnections;

    public ConnectionPool() {
        logger.info("Creating connection pool...");
    }

    @PostConstruct
    private void init() {
        availableConnections = new LinkedBlockingQueue<>(size);
        usedConnections = ConcurrentHashMap.newKeySet();
        logger.info("Connection pool was initialized.");
    }

    @Override
    public ProxyConnection getConnection() throws SQLException {
        return (ProxyConnection) super.getConnection();
    }

    @Override
    public ProxyConnection getConnection(String username, String password) throws SQLException {
        return (ProxyConnection) super.getConnection(username, password);
    }

    @Override
    protected ProxyConnection getConnectionFromDriver(Properties properties) throws SQLException {
        ProxyConnection connection;
        if (!availableConnections.isEmpty() || usedConnections.size() == size){
            try {
                connection = availableConnections.take();
                usedConnections.add(connection);
            } catch (InterruptedException e) {
                logger.fatal("Error while placing connection back.", e);
                throw new RuntimeException("Error while getting connection.", e);
            }
        }
        else {
            connection = createConnection(properties);
            usedConnections.add(connection);
        }
        return connection;
    }

    private boolean releaseConnection(ProxyConnection connection){
        boolean isRemoved = usedConnections.remove(connection);
        try {
            if (isRemoved) {
                availableConnections.put(connection);
                logger.debug("Proxy connection " +
                        connection + " was released from user, was added to available queue.");
            }
            return isRemoved;
        } catch (InterruptedException e) {
            logger.fatal("Error while placing connection back.", e);
            throw new RuntimeException("Error while placing connection back.", e);
        }
    }

    private ProxyConnection createConnection(Properties connectionProperties) throws SQLException{
        Connection connection = DriverManager.getConnection(
                (String) connectionProperties.get(RepositoryConfig.url),
                connectionProperties);
        ProxyConnection proxyConnection = (ProxyConnection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{ProxyConnection.class},
                (proxy, method, args) -> {
                    if (CLOSE_METHOD.equals(method.getName())) {
                        boolean isReleased = releaseConnection((ProxyConnection) proxy);
                        String log = isReleased ? "Proxy connection was returned to connection pool" :
                                "There was a trouble while returning connection to pool.";
                        logger.debug(log);
                        return isReleased;
                    }
                    else if (FORCE_CLOSE.equals(method.getName())){
                        connection.close();
                        return null;
                    }
                    if (!availableConnections.contains(connection)) {
                        return method.invoke(connection, args);
                    }
                    return null;
                });
        logger.debug("Proxy connection " + proxyConnection + " was created.");
        return proxyConnection;
    }

    @Override
    public void close() {
        logger.info("Connection pool is going to be destroyed...");
        availableConnections.forEach(ProxyConnection::forceClose);
        usedConnections.forEach(ProxyConnection::forceClose);
        deregisterDriver();
    }

    private void deregisterDriver(){
        Enumeration<Driver> enumDrivers = DriverManager.getDrivers();
        while (enumDrivers.hasMoreElements()) {
            Driver driver = enumDrivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info("Drivers were deregistered.");
            } catch (SQLException e) {
                logger.fatal("Error while deregistring drivers.", e);
                throw new RuntimeException("Error while deregistring drivers.", e);
            }
        }
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
