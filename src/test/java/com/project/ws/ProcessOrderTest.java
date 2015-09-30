package com.project.ws;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.project.ws.domain.Customer;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.domain.Order;
import com.project.ws.domain.Product;
import com.project.ws.domain.Vendor;
import com.project.ws.repository.CustomerAddressRepository;
import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.repository.CustomerRepository;
import com.project.ws.repository.OrderRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.repository.VendorRepository;

/**
 * 
 * @author manmeet
 *
 * @about This test class will test adding a customer, a vendor, customer address, customer billing details, product. 
 *		Also :  buying a product
 *				placing an order, canceling an order, 
 *				settling vendor account, processing payment
 *   
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessOrderTest {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	ProductRepository prodRepo;

	@Autowired
	CustomerAddressRepository addrRepo;
	
	@Autowired
	CustomerBillingRepository billRepo;
	
	@Autowired
	VendorRepository vendorRepo;
	
	Customer jaideep;
	CustomerAddress jaideepAddr;
	CustomerBillingDetails jaideepBilling;
	Product shampoo;
	Vendor alibaba;
	Integer orderId;
	
	
	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() throws ParseException {
		
		//Date processing
		String dateString = "2019-09-01";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dateString);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		jaideep = new Customer("Jaideep Singh", "Mokha", "jaideep@gmail.com", "OrderPassword");
		//custRepo.addCustomer(jaideep.getCustFirstname(), jaideep.getCustLastName(), jaideep.getCustEmail(), jaideep.getCustPassword());
		custRepo.save(jaideep);
		
		alibaba = new Vendor("Ali Baba", "Main Street", "#123", "Xougong", "HK", "China", "12345", "Y");
		//vendorRepo.addVendor(alibaba);
		vendorRepo.save(alibaba);
		
		shampoo = new Product("Head n Shoulders", "Bath&Body", "HeadnShoulders Shampoo", 9.50, 15, alibaba.getVendorId());
		//prodRepo.addProduct(shampoo);
		prodRepo.save(shampoo);
		
		jaideepAddr = new CustomerAddress(jaideep.getCustId(), "6000 Road", "apt 111", "Chicago", "IL", "60001", "5555555555");
		//addrRepo.addCustomerAddress(jaideep.getCustId(), jaideepAddr);
		addrRepo.save(jaideepAddr);
		
		jaideepBilling = new CustomerBillingDetails(jaideep.getCustId(), "VISA", "1234567890123456", "123", sqlDate, "Jaideep Singh", "6000 Road", "apt 111", "Chicago", "IL", "60001");
		//billRepo.addCardDetails(jaideepBilling);
		billRepo.save(jaideepBilling);
		
	}
	
	@Test
	public void canProcessOrder() {

		//select a product for buying and add to a cart
		Integer count;
		count = prodRepo.buyProduct(jaideep.getCustId(), shampoo, 2);
		assert count == 1;	//assertion for checking that a product was indeed added to cart

		//place the order
		count = 0;
		count = orderRepo.placeOrder(jaideep.getCustId(), jaideepBilling.getCustBillId());
		assert count == 1;
		
		//check if order has been placed for the customer
		List<Order> orderList = orderRepo.findAllActiveOrders(jaideep.getCustId());
		System.out.println("order retrieved for customer " + jaideep.getCustId() + " is - " + orderId);
		System.out.println("jaideep's customer id is " + jaideep.getCustId());
		//assert orderId == jaideep.getCustId();
		assertEquals(jaideep.getCustId(), orderList.get(0).getCustomerId());
		
		orderId = orderRepo.findActiveOrder(jaideep.getCustId());
		//settle vendor account
		count = 0;
		count = vendorRepo.settleAccount(alibaba.getVendorId(), orderRepo.findOne(orderId).getOrderAmount(), "credit");
		assert count == 1;
		
		//check if order can be cancelled
		count = 0;
		orderId = orderRepo.findActiveOrder(jaideep.getCustId());
		count = orderRepo.cancelOrder(orderId);
		assert count == 1;
		
		//settle vendor account
		count = 0;
		count = vendorRepo.settleAccount(alibaba.getVendorId(), orderRepo.findOne(orderId).getOrderAmount(), "debit");
		assert count == 1;
		
	}
	
	@After
	public void tearDown() {
		
		System.out.println("in Tear Down");
	}
	

}
