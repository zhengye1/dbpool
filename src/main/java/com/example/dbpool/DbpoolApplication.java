package com.example.dbpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
public class DbpoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbpoolApplication.class, args);

    }

}
