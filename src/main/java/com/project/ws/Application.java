package com.project.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.project.ws.database.domain.Customer;
import com.project.ws.database.domain.CustomerBillingDetails;
import com.project.ws.database.domain.Order;
import com.project.ws.database.domain.Product;
import com.project.ws.database.repository.custom.*;

/**
 * This is the main class which will run the webapp as spring boot.
 * It sets up the application context of spring.
 * It also starts the embedded tomcat server,
 * and deploy this application. 
 */

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	ProductRepository prodRepo;
	
	@Autowired
	CustomerBillingRepository billRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Override
	public void run(String... strings) throws Exception {
	
		Integer billId = 0;
		Integer customerId = 0;
		Integer productId;
		Integer orderId = 0;
		Double productPrice;
		List<Product> productList;
		List<CustomerBillingDetails> billDetailList;
		
		//List all products
		log.info("-----listing all products-----");
		productList = prodRepo.findAll();
		for(Product product:productList) {
			log.info(product.toString());
		}
		
		//searching products with a search string
		log.info("-----searching products by product name - tide-----");
		productList = prodRepo.readByProductName("tide");
		for(Product product:productList) {
			log.info(product.toString());
		}
		
		//searching products with specific name
		log.info("-----listing products with quantity less than a specific number------");
		productList = prodRepo.getProductsWithQuantityLessThan(10);
		for(Product product:productList) {
			log.info(product.toString());
		}
		
		//deleting product based on name
		log.info("-----deleting products based on name------");
		Integer count = prodRepo.deleteProduct("olay");
		log.info("deleted products : " + count + " -- checking if they exist");
		productList = prodRepo.readByProductName("olay");
		if (productList.isEmpty()) 
			log.info("no products with name 'olay'");
		else 
			log.info("OOOPS!! products still exist");
		
		//updating a product name
		log.info("update the old product name to new ");
		count = prodRepo.updateProductName("Tide Sport", "Tide Sport Plus");
		productList = prodRepo.readByProductName("tide");
		for(Product product:productList) {
			log.info(product.toString());
		}
		
		//Find the Customer
		log.info("find customer Manmeet Kaur");
		List<Customer> custList = custRepo.findByCustFirstname("Manmeet Kaur");
		if (custList.isEmpty())
			log.info("ERROR!!! No customer exists with this name. Try Again Please");
		else {
			customerId = custList.get(0).getCustId();			
		}
		
		
		//Buy a Product
		log.info("-----searching products by specific name------");
		productList = prodRepo.findByProductName("Tide Heavy");
		productId = productList.get(0).getId();
		productPrice = productList.get(0).getPrice();
		
		prodRepo.buyProduct(customerId, productId, productPrice);
		log.info("check cart - entry created");
		
		//select Payment Method
		log.info("-----select the payment method by card VISA------");
		billDetailList = billRepo.findByCardType("VISA");
		if(billDetailList.isEmpty())
			log.info("ERROR!!! no card details of type VISA");
		else {
			log.info("card details found for VISA");
			billId = billDetailList.get(0).getCustBillId();
		}
		
//		orderRepo.updateCart(billId);
		
		log.info("placing an order for : " +customerId + " - " + billId);
		orderRepo.placeOrder(customerId, billId);

		orderId = orderRepo.findActiveOrder(customerId);
		log.info("active order for customer - " + customerId + "-" + orderId);
		

		log.info("get order status");
		List<Order> order = orderRepo.getOrderStatus(customerId, orderId);
		log.info("order details are: " + order.get(0).toString());

		log.info("ship order");
		orderRepo.shipOrder(customerId, orderId);
		
		log.info("cancelling the above order ");
		orderRepo.cancelOrder(orderId);	
		
//		log.info("find all orders for a customer");
//		orderRepo.findAll();
		
//		orderRepo.notifyCustomer(Integer customerId);
//		
//		orderRepo.notifyVendor(Integer vendorId);
//		
//		orderRepo.settleVendorAccount(Integer vendorId, Double OrderAmount);
//		

//

	}
}
