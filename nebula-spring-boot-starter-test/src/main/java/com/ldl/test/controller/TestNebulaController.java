package com.ldl.test.controller;

import com.ldl.nebula.bean.Player;
import com.ldl.nebula.service.GraphService;
import com.ldl.nebula.vo.NebulaResult;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lideliang
 * @date 2024/12/17 9:32
 */
@RestController
@RequestMapping("/nebula/test")
public class TestNebulaController {


    @Resource
    private GraphService graphService;

    @GetMapping("/1")
    public void test1() {
        NebulaResult<Player> res2 = graphService.query( "MATCH (v:player) RETURN v");
        NebulaResult<String> res3 = graphService.query( "MATCH (v:player) RETURN v");
        System.out.println();
    }

    @GetMapping("/2")
    public void test2() {
//        NebulaResult<String> res2 = graphService.query("MATCH p=(n:player)-[*..]-() RETURN p");
        NebulaResult<String> res3 = graphService.query("MATCH p=(n:player)-[*1..2]-() RETURN p");

        System.out.println();
    }

    @GetMapping("/3")
    public NebulaResult<String> test3(String ngql) {
        NebulaResult<String> res2 = graphService.query(ngql);
        return res2;
    }

}
