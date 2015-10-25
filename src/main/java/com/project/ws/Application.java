package com.project.ws;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.ws.domain.Order;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.OrderRequest;


@EnableAutoConfiguration
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
    private static final String baseUrl = "http://localhost:8080";
    private static String finalUrl;
	 
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    	
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<OrderRepresentation[]> orderResponse;
    	ResponseEntity<CartRepresentation[]> cartResponse;
    	ResponseEntity<CustomerRepresentation[]> customerResponse;
    	Map<String, Object> params;
    	
    	/*
    	 * Testing Customer Service
    	 */

    	/*
    	 * POST for Customer to add new customer
    	 */
    	finalUrl = baseUrl + "/customer/addCustomer";
    	CustomerRequest customerRequest = new CustomerRequest();
    	customerRequest.setFirstName("Bradley");
    	customerRequest.setLastName("Cooper");
    	customerRequest.setEmail("bradley.cooper@email.com");
    	
    	ResponseEntity<String> stringResponse = restTemplate.postForEntity(finalUrl, customerRequest, String.class);
        System.out.println("----POST to add new Customer -------------------------------------------------------------");
        System.out.println("Response Status " + stringResponse.getStatusCode());
        System.out.println("Response Headers " + stringResponse.getHeaders());
        System.out.println("Response Body " + stringResponse.getBody());
        System.out.println("-----------------------------------------------------------------------------------------");
    	
    	/*
    	 * GET for Customer by First Letter of First Name
    	 */
    	finalUrl = baseUrl + "/customer/firstLetter";
    	params = new HashMap<String, Object>();
    	params.put("letter", (String) "G");
    	
    	customerResponse = restTemplate.getForEntity(finalUrl, CustomerRepresentation[].class, params);
        System.out.println("----GET All Customers by first letter of first name --------------------------------------");
        System.out.println("Response Status " + customerResponse.getStatusCode());
        System.out.println("Response Headers " + customerResponse.getHeaders());
        System.out.println("Response Body " + Arrays.asList(customerResponse.getBody()).toString());
        System.out.println("-----------------------------------------------------------------------------------------");
        
    	/*
    	 * Processing for GET orders by customerId
    	 */
		finalUrl = baseUrl + "order/findAll/{customerId}";
		params = new HashMap<String, Object>();
		params.put("customerId", (Integer) 10000174);
		
        orderResponse = restTemplate.getForEntity(finalUrl, OrderRepresentation[].class, params);
        
        System.out.println("----GET All Orders by CustomerId-------------------------------------------------------------");
        System.out.println("Response Status " + orderResponse.getStatusCode());
        System.out.println("Response Headers " + orderResponse.getHeaders());
        System.out.println("Response Body " + Arrays.asList(orderResponse.getBody()).toString());
        System.out.println("-----------------------------------------------------------------------------------------");

        /*
    	 * Processing for GET ACTIVE orders by customerId
    	 */
		finalUrl = baseUrl + "order/findAll/activeOrders/{customerId}";
		params = new HashMap<String, Object>();
		params.put("customerId", (Integer) 10000174);
		
        orderResponse = restTemplate.getForEntity(finalUrl, OrderRepresentation[].class, params);
        
        System.out.println("----GET Active Orders by CustomerId-----------------------------------------------------------------");
        System.out.println("Response Status " + orderResponse.getStatusCode());
        System.out.println("Response Headers " + orderResponse.getHeaders());
        System.out.println("Response Body " + Arrays.asList(orderResponse.getBody()).toString());
        System.out.println("-----------------------------------------------------------------------------------------");

        /*
    	 * Processing for GET Request for Order Status by orderId
    	 */
		finalUrl = baseUrl + "order/checkOrderStatus/{orderId}";
		params = new HashMap<String, Object>();
		params.put("orderId", (Integer) 10000032);
		
        ResponseEntity<Order> response = restTemplate.getForEntity(finalUrl, Order.class, params);
        
        System.out.println("----GET Order Status by OrderId-----------------------------------------------------------------");
        System.out.println("Response Status " + response.getStatusCode());
        System.out.println("Response Headers " + response.getHeaders());
        System.out.println("Response Body " + response.getBody());
        System.out.println("-----------------------------------------------------------------------------------------");

        
        /*
         * POST for creating an Order using OrderRequest - populating a cart		
         */
		finalUrl = baseUrl + "order/createOrder";
		
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setCustomerId(10000196);
		orderRequest.setPrice(1.00);
		orderRequest.setProductId(10000086);
		orderRequest.setQuantity(10);
		cartResponse = restTemplate.postForEntity(finalUrl, orderRequest, CartRepresentation[].class);
		
		System.out.println("----Create an Order - Populate Cart -----------------------------------------------------------");
        System.out.println("Response Status " + cartResponse.getStatusCode());
        System.out.println("Response Headers " + cartResponse.getHeaders());
        System.out.println("Response Body " + Arrays.asList(cartResponse.getBody()).toString());
        System.out.println("-------------------------------------------------------------------------------------------");

		
        /*
         * POST for placing an Order using OrderRequest - actually placing an order		
         */
		finalUrl = baseUrl + "order/placeOrder";
		orderRequest.setCustomerId(10000196);
		orderRequest.setPrice(1.00);
		orderRequest.setProductId(10000086);
		orderRequest.setQuantity(10);
		ResponseEntity<OrderRepresentation> newOrderResponse = restTemplate.postForEntity(finalUrl, orderRequest, OrderRepresentation.class);
		
        System.out.println("----Place Order using OrderRequest -------------------------------------------------------------");
        System.out.println("Response Status " + newOrderResponse.getStatusCode());
        System.out.println("Response Headers " + newOrderResponse.getHeaders());
        System.out.println("Response Body " + newOrderResponse.getBody());
        System.out.println("-----------------------------------------------------------------------------------------");

    }
}