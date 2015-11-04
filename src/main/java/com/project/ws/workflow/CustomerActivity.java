package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Customer;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.repository.CustomerRepository;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;

@Component
@Transactional
@Service
public class CustomerActivity {

	private final CustomerRepository custRepo;
	
	@Autowired
	Customer newCustomer;
	
	@Autowired
	CustomerRepresentation customerRepresentation;
	
	@Autowired
	CustomerActivity(CustomerRepository custRepo) {
		this.custRepo = custRepo;
	}
	
	public CustomerRepresentation addCustomer(CustomerRequest customerRequest) {
		newCustomer = mapRequest(customerRequest);
		Integer count = custRepo.addCustomer(newCustomer.getCustFirstname(), newCustomer.getCustLastName(), newCustomer.getCustEmail(), newCustomer.getCustPassword());
		newCustomer = custRepo.findByCustFirstName(newCustomer.getCustFirstname());
		return mapRepresentation(newCustomer);
	}
	
	public String updateName(Integer customerId, String firstName, String lastName) {
		Integer count = custRepo.updateName(customerId, firstName, lastName);
		if(count == 0)
			return "Error updating customer " + customerId;
		else
			return "Updated customer " + customerId + " 's name successfully to " + firstName + " " + lastName;
	}

	public String updateEmail(Integer customerId, String email) {
		Integer count = custRepo.updateEmail(customerId, email);
		if(count == 0)
			return "Error updating customer " + customerId;
		else
			return "Updated customer " + customerId + " 's email successfully to " + email;
	}

	public String deleteCustomer(Integer customerId) {
		Integer count = custRepo.deleteCustomer(customerId);
		if(count == 0)
			return "Error deleting Customer";
		else
			return "Customer deleted Successfully";
	}
	
	public CustomerRepresentation getCustomerById(Integer customerId) {
		newCustomer = custRepo.findOne(customerId);
		return mapRepresentation(newCustomer);
	}
	
	public CustomerRepresentation getCustomersByFirstName(String name) {
		Customer customer = custRepo.findByCustFirstName(name);
		if(customer == null) return null;
		return mapRepresentation(customer);
	}
	
	public Boolean validateCustomer(Integer customerId) {
		Customer c = custRepo.findOne(customerId);
		if(c == null)
			return false;
		else 
			return true;
	}
	
	public Customer mapRequest(CustomerRequest customerRequest) {
		Customer customer = new Customer();
		customer.setCustFirstname(customerRequest.getFirstName());
		customer.setCustLastName(customerRequest.getLastName());
		customer.setCustEmail(customerRequest.getEmail());
		customer.setCustPassword(customerRequest.getPassword());
		return customer;
	}
	
	public CustomerRepresentation mapRepresentation(Customer customer) {
		customerRepresentation = new CustomerRepresentation();
		customerRepresentation.setCustFirstname(customer.getCustFirstname());
		customerRepresentation.setCustLastName(customer.getCustLastName());
		customerRepresentation.setCustEmail(customer.getCustEmail());
		customerRepresentation.setCustId(customer.getCustId());
		return customerRepresentation;
	}
	
//	public CustomerRepresentation mapRepresentation(Customer customer, CustomerAddress custAddr) {
//		customerRepresentation = new CustomerRepresentation();
//		customerRepresentation.setCustFirstname(customer.getCustFirstname());
//		customerRepresentation.setCustLastName(customer.getCustLastName());
//		customerRepresentation.setCustEmail(customer.getCustEmail());
//		customerRepresentation.setCustId(customer.getCustId());
//		return customerRepresentation;
//	}
}
