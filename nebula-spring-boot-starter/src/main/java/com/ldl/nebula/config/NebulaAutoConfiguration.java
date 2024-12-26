package com.ldl.nebula.config;

import com.ldl.nebula.factory.NebulaSessionFactory;
import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lideliang
 * @date 2024/12/17 9:16
 */
@Configuration
@EnableConfigurationProperties(NebulaGraphProperties.class)
public class NebulaAutoConfiguration {

    @Resource
    private NebulaGraphProperties properties; // 使用配置

    // 在Spring上下文中创建一个对象
    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public NebulaSessionFactory nebulaSessionFactory() {
        List<HostAddress> hostAddresses = new ArrayList<>();
        String[] hostList = properties.getHosts().split(",[ ]*");
        for (String host : hostList) {
            String[] hostParts = host.split(":");
            if (hostParts.length != 2 || !hostParts[1].matches("\\d+")) {
                throw new RuntimeException("Invalid host name set for Nebula: " + host);
            }
            hostAddresses.add(new HostAddress(hostParts[0], Integer.parseInt(hostParts[1])));
        }
        NebulaPoolConfig poolConfig = new NebulaPoolConfig();
        poolConfig.setMaxConnSize(properties.getMaxConn());
        NebulaPool pool = new NebulaPool();
        try {
            pool.init(hostAddresses, poolConfig);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unknown Nebula hosts");
        }
        return new NebulaSessionFactory(pool, properties.getUsername(), properties.getPassword());
    }

}
