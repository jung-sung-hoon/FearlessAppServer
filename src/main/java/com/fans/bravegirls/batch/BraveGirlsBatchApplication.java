package com.fans.bravegirls.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BraveGirlsBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BraveGirlsBatchApplication.class, args);
    }

}
