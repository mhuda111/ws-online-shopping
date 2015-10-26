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
import com.project.ws.representation.ProductRepresentation;


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
//    	testCustomerService();
//    	testOrderService();
    	testProductService();
//    	testVendorService();
    }
    
    public static void testCustomerService() {
    	/*
    	 * Declare Variables
    	 */
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<CustomerRepresentation> customerResponse;
    	ResponseEntity<String> stringResponse;
    	Map<String, Object> params;

    	/*
    	 * POST for Customer to add new customer
    	 */
    	finalUrl = baseUrl + "/customer/addCustomer";
    	CustomerRequest customerRequest = new CustomerRequest("Bradley", "Cooper", "bradley.cooper@email.com");
    	
    	stringResponse = restTemplate.postForEntity(finalUrl, customerRequest, String.class);
        displayStats(stringResponse, "POST to add a new Customer");
    	/*
    	 * GET for Customer by First Letter of First Name
    	 */
        //finalUrl = baseUrl + "/customer/firstName/{fname}";
        finalUrl = baseUrl + "/customer/firstName/?fname={fname}";
    	params = new HashMap<String, Object>();
    	params.put("fname", (String) "Bradley");
    	
    	customerResponse = restTemplate.getForEntity(finalUrl, CustomerRepresentation.class, params);
        displayStats(customerResponse, "GET all customers using first name");
        
        Integer id = customerResponse.getBody().getCustId();
        System.out.println("**********" + id);
        /*
    	 * GET for Customer by CustomerId
    	 */
    	finalUrl = baseUrl + "/customer/?customerId={customerId}";
    	params = new HashMap<String, Object>();
    	params.put("customerId", (Integer) id);
    	
    	customerResponse = restTemplate.getForEntity(finalUrl, CustomerRepresentation.class, params);
        displayStats(customerResponse, "GET all customers using first name");
        
    }
    
    public static void testProductService() {
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<ProductRepresentation[]> productResponse;
    	Map<String, Object> params;
    	
    	/*
    	 * GET all the products
    	 */
    	finalUrl = baseUrl + "/product";
    	
    	productResponse = restTemplate.getForEntity(finalUrl, ProductRepresentation[].class);
        displayStats(productResponse, "GET all products");
    	/*
    	 * GET products by name
    	 */
        finalUrl = baseUrl + "/product/productName/?name={name}";
    	params = new HashMap<String, Object>();
    	params.put("name", (String) "Shampoo");
    	productResponse = restTemplate.getForEntity(finalUrl, ProductRepresentation[].class, params);
        displayStats(productResponse, "GET all products matching param");
    	/*
    	 * GET product by id
    	 */
    	/*
    	 * POST to add a product
    	 */
    	/*
    	 * DELETE to delete a product
    	 */
    }
    
    public static void testOrderService() {
    	/*
    	 * Declare variables
    	 */
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<OrderRepresentation[]> orderResponse;
    	ResponseEntity<CartRepresentation[]> cartResponse;
    	Map<String, Object> params;
    	/*
    	 * Processing for GET orders by customerId
    	 */
		finalUrl = baseUrl + "order/findAll/{customerId}";
		params = new HashMap<String, Object>();
		params.put("customerId", (Integer) 10000174);
		
        orderResponse = restTemplate.getForEntity(finalUrl, OrderRepresentation[].class, params);
        displayStats(orderResponse, "GET orders by customerId");
        /*
    	 * Processing for GET ACTIVE orders by customerId
    	 */
		finalUrl = baseUrl + "order/findAll/activeOrders/{customerId}";
		params = new HashMap<String, Object>();
		params.put("customerId", (Integer) 10000174);
		
        orderResponse = restTemplate.getForEntity(finalUrl, OrderRepresentation[].class, params);
        displayStats(orderResponse, "GET active orders by customerId");
        /*
    	 * Processing for GET Request for Order Status by orderId
    	 */
		finalUrl = baseUrl + "order/checkOrderStatus/{orderId}";
		params = new HashMap<String, Object>();
		params.put("orderId", (Integer) 10000032);
		
        ResponseEntity<Order> response = restTemplate.getForEntity(finalUrl, Order.class, params);
        displayStats(response, "GET order status using orderId");
        
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
        displayStats(cartResponse, "POST populate cart using Order Request");
		
        /*
         * POST for placing an Order using OrderRequest - actually placing an order		
         */
		finalUrl = baseUrl + "order/placeOrder";
		ResponseEntity<OrderRepresentation> newOrderResponse = restTemplate.postForEntity(finalUrl, orderRequest, OrderRepresentation.class);
        displayStats(newOrderResponse, "POST Place Order using OrderRequest");

    }
    
    public static void testVendorService() {}
    
    public static void displayStats(ResponseEntity<?> response, String message) {
        System.out.println("----" + message + "-------------------------------------------------------------");
        System.out.println("Response Status " + response.getStatusCode());
        System.out.println("Response Headers " + response.getHeaders());
        System.out.println("Response Body " + Arrays.asList(response.getBody()));
        System.out.println("-----------------------------------------------------------------------------------------");

    }
}