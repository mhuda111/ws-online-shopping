package com.project.ws;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.project.ws.domain.Customer;
import com.project.ws.repository.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CustomerRepositoryTest {
	
	@Autowired
	CustomerRepository custRepo;
	
	Customer manmeet;
	
	@Value("${local.server.port}")
	int port;
	
	@Before
	public void setUp() {
		manmeet = new Customer("Jaideep", "", "manmeet11@gmail.com", "P@ssword");

		System.out.println("****in setup");
		
		custRepo.save(manmeet);
		
		custRepo.addCustomer(manmeet.getCustFirstname(), manmeet.getCustLastName(), manmeet.getCustEmail(), manmeet.getCustPassword());
		
		System.out.println("****in setup - After addition");
	}
	
	@Test
	public void canSelect() {
		List<Customer> custList = custRepo.findByCustFirstname("Jaideep");

		System.out.println("fname : " + custList.get(0).getCustFirstname());
		System.out.println("lname : " + custList.get(0).getCustLastName());

		assert custList.get(0).getCustFirstname().equals("Jaideep");
	}

	
	@Test
	public void canUpdate() {
		Integer count;
		List<Customer> custList = custRepo.findByCustFirstname("Jaideep");
		
		count = custRepo.updateName(custList.get(0).getCustId(), "Jaideep Singh", "Mokha");
		assert count == 1;
		
		custList = custRepo.findByCustFirstname("Jaideep Singh");
		System.out.println("fname : " + custList.get(0).getCustFirstname());
		System.out.println("lname : " + custList.get(0).getCustLastName());
		
		assert custList.get(0).getCustFirstname().equals("Jaideep Singh");
		assert custList.get(0).getCustLastName().equals("Mokha");		
	}
	
	@After
	public void tearDown() {
		custRepo.delete(manmeet);
	}
		
}
