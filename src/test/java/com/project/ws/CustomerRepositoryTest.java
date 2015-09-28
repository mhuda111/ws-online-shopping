package com.project.ws;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.project.ws.database.domain.Customer;
import com.project.ws.database.repository.custom.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CustomerRepositoryTest {
	
	@Autowired
	CustomerRepository custRepo;
	
	Customer manmeet;
	Customer asif;
	Customer syeda;
	
	@Value("${local.server.port}")
	int port;
	
	@Before
	public void setUp() {
		manmeet = new Customer("Jaideep", "", "manmeet11@gmail.com", "P@ssword");
		asif = new Customer("Mohammad Asif", "Huda", "asif.huda@gmail.com", "P@ssword");
		syeda = new Customer("Syeda", "Nowreen", "syeda.nowreen@gmail.com", "P@ssword");
		
		manmeet.setCustId(1111);
		asif.setCustId(2222);
		syeda.setCustId(3333);
		
		custRepo.save(Arrays.asList(manmeet, asif, syeda));
	}
	
	@Test
	public void canSelect() {
		List<Customer> custList = custRepo.findByCustFirstname("Jaideep");
//		List<Customer> custList = custRepo.findAll();
//		
//		for(Customer customer:custList) {
//			System.out.println("fname : " + customer.getCustFirstname());
//			System.out.println("lname : " + customer.getCustLastName());			
//		}

		System.out.println("fname : " + custList.get(0).getCustFirstname());
		System.out.println("lname : " + custList.get(0).getCustLastName());
		
		custList = custRepo.findByCustFirstname("Mohammad Asif");
		assert custList.get(0).getCustFirstname() == "Mohammad Asif";
		
		System.out.println("fname : " + custList.get(0).getCustFirstname());
		System.out.println("lname : " + custList.get(0).getCustLastName());
		
		custList = custRepo.findByCustFirstname("Syeda");
		assert custList.get(0).getCustFirstname() == "Syeda";
		
		System.out.println("fname : " + custList.get(0).getCustFirstname());
		System.out.println("lname : " + custList.get(0).getCustLastName());
		
	}
	
	@Test
	public void canUpdate() {
		Integer count;

		count = custRepo.updateName(1111, "Jaideep Singh", "Mokha");
		assert count == 1;
		
		List<Customer> custList = custRepo.findByCustFirstname("Jaideep Singh");
		assert custList.get(0).getCustFirstname() == "Jaideep Singh";
		assert custList.get(0).getCustLastName() == "Mokha";
		
	}
		
}
