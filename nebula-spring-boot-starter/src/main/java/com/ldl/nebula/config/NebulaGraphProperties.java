package com.ldl.nebula.config;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lideliang
 * @date 2024/12/17 9:08
 */

@ConfigurationProperties(prefix = "nebula")
@Setter
@Getter
public class NebulaGraphProperties {

    private String space = "sjlzjc2q";
    private String hosts = "127.0.0.1:9669";
    private int maxConn = 100;
    private String username = "root";
    private String password = "nebula";

}