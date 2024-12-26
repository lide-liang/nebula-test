package com.ldl.nebula.test;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * @author lideliang
 * @date 2024/12/16 14:48
 */
public class TestCreateNebulaPool {

    private static final Logger log = LoggerFactory.getLogger(TestCreateNebulaPool.class);


    /**
     * 创建 NebulaPool 连接池
     */
    @Test
    public void createNebulaPool() throws UnknownHostException {
        NebulaPool pool = new NebulaPool();
        NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
        nebulaPoolConfig.setMaxConnSize(100);
        List<HostAddress> addresses = Arrays.asList(new HostAddress("192.168.100.133", 9669));
        Boolean initResult = pool.init(addresses, nebulaPoolConfig);
        if (!initResult) {
            log.error("pool init failed.");
            return;
        } else {
            log.error("pool init success.");
        }
    }



}
