package com.gl.springbootapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.gl.springbootdao.mapper"})
@EnableScheduling
@ComponentScan({"com.gl"})
public class SpringbootApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiApplication.class, args);
    }

}
