package com.epam.esm.repository.pool;

import java.sql.Connection;

/**
 * Proxied Connection object
 */
public interface ProxyConnection extends Connection {
    /**
     * Method that physically closes the connection.
     * Method close is proxied and only returns connection to the pool.
     */
    void forceClose();
}
