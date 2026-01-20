package com.mac4.yeopabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YeopaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YeopaBackendApplication.class, args);
    }

}
