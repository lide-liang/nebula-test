package com.ldl.nebula.factory;

import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;

/**
 * @author lideliang
 * @date 2024/12/16 15:12
 */
public class NebulaSessionFactory {
    private final NebulaPool pool;
    private final String username;
    private final String password;

    public NebulaSessionFactory(NebulaPool pool, String username, String password) {
        this.pool = pool;
        this.username = username;
        this.password = password;
    }

    public Session getSession() {
        try {
            return pool.getSession(username, password, false);
        } catch (NotValidConnectionException | IOErrorException | AuthFailedException | ClientServerIncompatibleException e) {
            throw new RuntimeException("Nebula session exception", e);
        }
    }

    public void close() {
        pool.close();
    }
}