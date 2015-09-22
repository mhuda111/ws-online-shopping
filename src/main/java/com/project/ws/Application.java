package com.project.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main class which will run the webapp as spring boot.
 * It sets up the application context of spring.
 * It also starts the embedded tomcat server,
 * and deploy this application. 
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
