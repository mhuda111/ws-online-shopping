package com.project.ws;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Application {

	@Autowired
	private DataSource dataSource;
	
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}