//package com.project.ws;
//
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.project.ws.domain.Customer;
//import com.project.ws.workflow.CustomerRepository;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest("server.port:0")
//public class CustomerServiceTest {
//	
//	@Autowired
//	CustomerRepository custRepo;
//	
//	Customer jaideep;
//	
//	@Value("${local.server.port}")
//	int port;
//	
//	@Before
//	public void setUp() {
//		jaideep = new Customer("Jaideep", "", "jaideep@gmail.com", "P@ssword");
//
//		System.out.println("****in setup****");
//		
//		custRepo.save(jaideep);
//		//custRepo.addCustomer(jaideep.getCustFirstname(), jaideep.getCustLastName(), jaideep.getCustEmail(), jaideep.getCustPassword());
//		
//		System.out.println("****in setup - After addition****");
//	}
//	
//	@Test
//	public void canSelect() {
//		List<Customer> custList = custRepo.findByCustFirstname("Jaideep");
//
//		System.out.println(custList.get(0).toString());
//
//		assert custList.get(0).getCustFirstname().equals("Jaideep");
//	}
//	
//	@Test
//	public void canSelectAll() {
//		List<Customer> custList = custRepo.findAll();
//		
//		for(Customer customer:custList) {
//			System.out.println(customer.toString());
//		}
//		
//		assert custList.size() > 0;
//	}
//
//	
//	@Test
//	public void canUpdate() {
//		Integer count;
//		List<Customer> custList = custRepo.findByCustFirstname("Jaideep");
//		
//		count = custRepo.updateName(custList.get(0).getCustId(), "Jaideep Singh", "Mokha");
//		assert count == 1;
//		
//		custList = custRepo.findByCustFirstname("Jaideep Singh");
//		System.out.println(custList.get(0).toString());
//
//		assert custList.get(0).getCustFirstname().equals("Jaideep Singh");
//		assert custList.get(0).getCustLastName().equals("Mokha");		
//	}
//	
//	
//	@After
//	public void tearDown() {
//		custRepo.delete(jaideep);
//	}
//		
//}
