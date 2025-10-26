package org.example.uberproject_location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class UberProjectLocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberProjectLocationApplication.class, args);
    }

}
