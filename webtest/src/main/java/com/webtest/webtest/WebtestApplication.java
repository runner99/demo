package com.webtest.webtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})

public class WebtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebtestApplication.class, args);
    }

}
