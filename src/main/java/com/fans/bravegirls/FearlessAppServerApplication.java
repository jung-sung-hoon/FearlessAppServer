package com.fans.bravegirls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FearlessAppServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FearlessAppServerApplication.class, args);
    }

}
