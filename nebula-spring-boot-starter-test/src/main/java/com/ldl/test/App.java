package com.ldl.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author lideliang
 * @date 2024/12/17 9:28
 */
@SpringBootApplication(scanBasePackages = {"com.ldl.test"},  exclude = {DataSourceAutoConfiguration.class})
public class App {

    public static void main(String[] args) {
        new SpringApplication(App.class).run(args);
    }
}