package com.dr.backsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dr.backsys.dao")
public class BacksysApplication {

    public static void main(String[] args) {
        SpringApplication.run(BacksysApplication.class, args);
    }

}
