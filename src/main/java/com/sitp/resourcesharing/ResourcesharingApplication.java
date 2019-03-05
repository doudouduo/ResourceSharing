package com.sitp.resourcesharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@EnableAutoConfiguration
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ResourcesharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourcesharingApplication.class, args);
    }
}
