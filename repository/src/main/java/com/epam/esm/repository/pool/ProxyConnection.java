package com.epam.esm.repository.pool;

import java.sql.Connection;

/**
 * Proxied Connection object
 */
public interface ProxyConnection extends Connection {
    /**
     * Method to close the connection physically.
     * {@link ProxyConnection#close()} Method to return the connection to the pool.
     */
    void forceClose();
}
