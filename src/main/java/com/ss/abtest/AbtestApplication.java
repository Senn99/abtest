package com.ss.abtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableScheduling
@MapperScan("com.ss.abtest.mapper")
@SpringBootApplication
public class AbtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbtestApplication.class, args);
    }

}
