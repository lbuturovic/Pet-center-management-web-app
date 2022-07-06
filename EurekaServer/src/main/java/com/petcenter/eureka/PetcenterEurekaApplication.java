package com.petcenter.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PetcenterEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetcenterEurekaApplication.class, args);
    }

}
