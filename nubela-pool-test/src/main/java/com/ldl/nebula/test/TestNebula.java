package com.ldl.nebula.test;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import org.junit.Before;
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
public class TestNebula {

    private static final Logger log = LoggerFactory.getLogger(TestNebula.class);

    NebulaPool pool = new NebulaPool();

    /**
     * 创建 NebulaPool 连接池
     */
    @Before
    public void createNebulaPool() throws UnknownHostException {
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

    /**
     * 创建一个SPACE
     * 然后使用这个SPACE
     * 创建一个TAG person
     * 创建一个EDGE like
     * @throws IOErrorException
     * @throws AuthFailedException
     * @throws ClientServerIncompatibleException
     * @throws NotValidConnectionException
     */
    @Test
    public void testCreate() throws IOErrorException, AuthFailedException, ClientServerIncompatibleException, NotValidConnectionException {
        Session session = pool.getSession("root", "nebula", false);
        String createSchema = "CREATE SPACE IF NOT EXISTS test(vid_type=fixed_string(20)); "
                            + "USE test;"
                            + "CREATE TAG IF NOT EXISTS person(name string, age int);"
                            + "CREATE EDGE IF NOT EXISTS like(likeness double)";
        ResultSet resp = session.execute(createSchema);
        if (!resp.isSucceeded()) {
            log.error(String.format("Execute: `%s', failed: %s",
                    createSchema, resp.getErrorMessage()));
            System.exit(1);
        }
    }

    /**
     * 添加一个点记录
     * @throws IOErrorException
     * @throws AuthFailedException
     * @throws ClientServerIncompatibleException
     * @throws NotValidConnectionException
     */
    @Test
    public void testInsertVertex() throws IOErrorException, AuthFailedException, ClientServerIncompatibleException, NotValidConnectionException {
        Session session = pool.getSession("root", "nebula", false);
        String insertVertexes = "INSERT VERTEX person(name, age) VALUES "
                + "'Bob':('Bob', 10), "
                + "'Lily':('Lily', 9), "
                + "'Tom':('Tom', 10), "
                + "'Jerry':('Jerry', 13), "
                + "'John':('John', 11);";
        ResultSet resp = session.execute(insertVertexes);
        if (!resp.isSucceeded()) {
            log.error(String.format("Execute: `%s', failed: %s",
                    insertVertexes, resp.getErrorMessage()));
            System.exit(1);
        }
    }

    @Test
    public void testQuery() throws IOErrorException, AuthFailedException, ClientServerIncompatibleException, NotValidConnectionException {
        Session session = pool.getSession("root", "nebula", false);
        String query = "GO FROM \"Bob\" OVER like "
                + "YIELD $^.person.name, $^.person.age, like.likeness";
        ResultSet resp = session.execute(query);
        if (!resp.isSucceeded()) {
            log.error(String.format("Execute: `%s', failed: %s",
                    query, resp.getErrorMessage()));
            System.exit(1);
        }
//        printResult(resp);
    }

    @Test
    public void test12() throws IOErrorException, AuthFailedException, ClientServerIncompatibleException, NotValidConnectionException {
//        // 创建 Nebula Graph 客户端配置
//        GraphClientConfig config = new GraphClientConfig();
//        config.setGraphAddress("127.0.0.1:9669"); // 替换为你的 Nebula Graph 地址和端口
//
//        // 创建连接池
//        GraphConnectionPool pool = new GraphConnectionPool(config);

        // 获取客户端
        Session session = pool.getSession("root", "nebula", false);

        // 执行查询
//        String query = "MATCH p=(n:player)-[e]->(v) RETURN n LIMIT 10"; // 示例查询
        String query = "use basketballplayer; MATCH p=(n:player)-[e]->(v) RETURN p";
        ResultSet resultSet = session.execute(query);
        // 输出结果
        System.out.println();

    }

}
