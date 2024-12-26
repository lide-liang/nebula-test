package com.ldl.nebula.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author lideliang
 * @date 2024/12/16 17:26
 */
@RestController("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Resource
    private GraphService graphService;

    @GetMapping("/1")
    public void test1() {
        String gql = "match (v:ADDR)-[e]-() where id(v)==\"ADD:123123\" return v,e limit 100";
        NebulaResult<Serializable> res = graphService.query("basketballplayer", gql);
        log.info(JacksonUtil.compress(res));
//        Assertions.assertThat(res).isNotNull();
        System.out.println();
    }

    @GetMapping("/2")
    public void test2() {
        NebulaResult<String> res2 = graphService.query("basketballplayer", "MATCH (v:player) RETURN v");
        System.out.println();
    }

    @GetMapping("/3")
    public void test3() {
        NebulaResult<EdgeId> res3 = graphService.query("basketballplayer", "MATCH ()-[e:follow]-() RETURN e");
        System.out.println();
    }

}
