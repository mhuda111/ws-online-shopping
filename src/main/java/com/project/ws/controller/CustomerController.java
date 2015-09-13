package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Customer;
import com.project.ws.repository.CustomerRepository;

@RestController
public class CustomerController {
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@RequestMapping("/customer")
    public List<Customer> getCustomers(HttpServletRequest request) {
		String name = request.getParameter("name");
    	return customerRepository.findByName(name);
    }

}
