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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.ws.domain.Order;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustAddrRepresentation;
import com.project.ws.representation.CustomerRequest;
import com.project.ws.representation.AddressRequest;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.OrderRequest;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.VendorRepresentation;
import com.project.ws.representation.VendorRequest;


@EnableAutoConfiguration
@Configuration
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
    private static final String baseUrl = "http://localhost:8080/";
    private static String finalUrl;
	 
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
//    	testCustomerService();
//    	testOrderService();
//    	testProductService();
//    	testVendorService();
    	testCustomerAddressService();
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
    	customerResponse = restTemplate.postForEntity(finalUrl, customerRequest, CustomerRepresentation.class);
        displayStats(customerResponse, "POST to add a new Customer");
    	
        /*
    	 * GET for Customer by First Name
    	 */
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
        
        params = new HashMap<String, Object>();
        params.put("customerId", (Integer) id);
        
        //restTemplate.delete(finalUrl, params); 
        
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
    
    public static void testVendorService() {
       	/*
    	 * Declare variables
    	 */
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<VendorRepresentation> vendorResponse;
    	VendorRequest vendorRequest = new VendorRequest();
    	Map<String, Object> params;
    	/*
    	 * Processing for GET vendor by Id
    	 */
		finalUrl = baseUrl + "vendor/?vendorId={vendorId}";
		params = new HashMap<String, Object>();
		params.put("vendorId", (Integer) 10000025);
		log.info("url is" + finalUrl);
        vendorResponse = restTemplate.getForEntity(finalUrl, VendorRepresentation.class, params);
        displayStats(vendorResponse, "GET vendors by Id");
        
    	/*
    	 * Processing for GET vendor by name
    	 */
		finalUrl = baseUrl + "vendor/?vendorName={vendorName}";
		params = new HashMap<String, Object>();
		params.put("vendorName", (String) "SELF");
		
        vendorResponse = restTemplate.getForEntity(finalUrl, VendorRepresentation.class, params);
        displayStats(vendorResponse, "GET vendor by name");
    	
        /*
    	 * Processing for POST vendor to add new
    	 */
		finalUrl = baseUrl + "vendor/addVendor/";
		vendorRequest.setVendorName("NEW Test Vendor");
		vendorRequest.setVendorAddrLine1("111 E Pearson St");
		vendorRequest.setVendorCity("Chicago");
		vendorRequest.setVendorCountry("USA");
		vendorRequest.setVendorState("IL");
		vendorRequest.setVendorZipCode("60601");
		
        vendorResponse = restTemplate.postForEntity(finalUrl, vendorRequest, VendorRepresentation.class);
        displayStats(vendorResponse, "GET vendors by name");
        
    	/*
    	 * Processing for PUT to update vendor Name
    	 */
		finalUrl = baseUrl + "vendor/?vendorId={vendorId}&vendorName={vendorName}";
		params = new HashMap<String, Object>();
		params.put("vendorId", (Integer)10000025);
		params.put("vendorName", "Self Vendor");
		vendorResponse = restTemplate.exchange(finalUrl, HttpMethod.PUT, null, VendorRepresentation.class, params);
        displayStats(vendorResponse, "PUT update vendor name");
        
    }
    
    public static void testCustomerAddressService() {
       	/*
    	 * Declare variables
    	 */
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<CustAddrRepresentation> addrResponse;
    	ResponseEntity<String> stringResponse;
    	ResponseEntity<CustAddrRepresentation[]> addrListResponse;
    	AddressRequest addrRequest = new AddressRequest();
    	Map<String, Object> params;
    	
    	/*
    	 * Processing for GET vendor by Id
    	 */
		finalUrl = baseUrl + "customeraddress/?customerId={customerId}";
		params = new HashMap<String, Object>();
		params.put("customerId", (Integer) 10000174);
		addrListResponse = restTemplate.getForEntity(finalUrl, CustAddrRepresentation[].class, params);
        displayStats(addrListResponse, "GET customer address by customer Id");

    	/*
    	 * Processing for POST to add customerAddress
    	 */
		finalUrl = baseUrl + "customeraddress/add/";
		addrRequest.setCustomerId(10000174);
		addrRequest.setAddrLine1("My new address");
		addrRequest.setCity("imagine");
		addrRequest.setState("GS");
		addrRequest.setZipCode("99999");
		
		stringResponse = restTemplate.postForEntity(finalUrl, addrRequest, String.class);
        displayStats(stringResponse, "POST to add new customer address");
        
    	/*
    	 * Processing for PUT to update customerAddress
    	 */
        
		finalUrl = baseUrl + "customeraddress/update/";
		
		addrRequest.setCustomerId(10000174);
		addrRequest.setAddrId(10000022);
		addrRequest.setAddrLine1("My new address");
		addrRequest.setAddrLine2("IS FAKE");
		addrRequest.setCity("imagine");
		addrRequest.setState("GS");
		addrRequest.setZipCode("99999");
		
		HttpEntity<AddressRequest> addrEntity = new HttpEntity<AddressRequest>(addrRequest);
		addrResponse = restTemplate.exchange(finalUrl, HttpMethod.PUT, addrEntity, CustAddrRepresentation.class);
        displayStats(addrResponse, "PUT to update existing customer address");
    }
    
    public static void displayStats(ResponseEntity<?> response, String message) {
        System.out.println("----" + message + "-------------------------------------------------------------");
        System.out.println("Response Status " + response.getStatusCode());
        System.out.println("Response Headers " + response.getHeaders());
        System.out.println("Response Body " + Arrays.asList(response.getBody()));
        System.out.println("-----------------------------------------------------------------------------------------");

    }
}