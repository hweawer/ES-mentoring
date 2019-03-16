package com.epam.esm.pool;

import com.epam.esm.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final String RELEASE_METHOD = "release";
    private static final String COMPARE_METHOD = "compareTo";

    @Autowired
    private Properties connectionProperties;
    public static Integer size;
    private static ConnectionPool instance = new ConnectionPool();

    private BlockingQueue<ProxyConnection> availableConnections;
    private Set<ProxyConnection> usedConnections;

    private static Lock lock = new ReentrantLock();

    private ConnectionPool() {
        logger.info("Creating connection pool...");
    }

    public static ConnectionPool getInstance(){
        if (instance == null){
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    logger.debug("Connection pool instance is created.");
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @PostConstruct
    private void init() {
        availableConnections = new LinkedBlockingQueue<>(size);
        usedConnections = new ConcurrentSkipListSet<>();
        logger.info("Connection pool was initialized.");
    }

    public ProxyConnection getConnection() throws SQLException {
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
            connection = createConnection(connectionProperties);
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
                (String) connectionProperties.get(AppConfig.url),
                connectionProperties);
        ProxyConnection proxyConnection = (ProxyConnection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{ProxyConnection.class},
                (proxy, method, args) -> {
                    if (RELEASE_METHOD.equals(method.getName())) {
                        boolean isReleased = releaseConnection((ProxyConnection) proxy);
                        String log = isReleased ? "Proxy connection was returned to connection pool" :
                                "There was a trouble while returning connection to pool.";
                        logger.debug(log);
                        return isReleased;
                    }
                    else if (COMPARE_METHOD.equals(method.getName())){
                        return proxy.hashCode() - args[0].hashCode();
                    }
                    return method.invoke(connection, args);
                });
        logger.debug("Proxy connection " + proxyConnection + " was created.");
        return proxyConnection;
    }

    @PreDestroy
    public void destroy() throws SQLException{
        logger.info("Connection pool is going to be destroyed...");
        for (ProxyConnection proxyConnection : availableConnections) {
            proxyConnection.close();
        }
        for (ProxyConnection usedConnection : usedConnections) {
            usedConnection.close();
        }
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
}
