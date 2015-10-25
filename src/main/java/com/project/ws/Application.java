package com.project.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.OrderRequest;


@EnableAutoConfiguration
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
    private static final String baseUrl = "http://localhost:8080/order";
    private static String finalUrl;
	 
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    	
    	RestTemplate restTemplate = new RestTemplate();
    	
		finalUrl = baseUrl + "/findAll/{customerId}";
		System.out.println("=========" + finalUrl);
		Map<String, Integer>  params = new HashMap<String, Integer>();
		params.put("customerId", 10000174);
		
        ResponseEntity<OrderRepresentation[]> response = restTemplate.getForEntity(finalUrl, OrderRepresentation[].class, params);
        
        System.out.println("Response Status " + response.getStatusCode());
        System.out.println("Response Headers " + response.getHeaders());
        System.out.println("Response Body " + Arrays.asList(response.getBody()).toString());

//REST Template not passing the orderRequest object to the server method		
//		finalUrl = baseUrl + "/createOrder";
//		CartRepresentation[] cartRepresentation;
//		OrderRequest orderRequest = new OrderRequest();
//		orderRequest.setCustomerId(10000196);
//		orderRequest.setPrice(1.00);
//		orderRequest.setProductId(10000086);
//		orderRequest.setQuantity(10);
//		System.out.println("==============" + finalUrl);
//		System.out.println("==============" + orderRequest.toString());
//		cartRepresentation = restTemplate.postForObject(finalUrl, orderRequest, CartRepresentation[].class);
//		System.out.println("after post " + cartRepresentation);
    }
}