package com.epam.esm.pool;

import java.sql.Connection;

public interface ProxyConnection extends Connection, Comparable<ProxyConnection> {
    void release();
}
