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
import com.project.ws.domain.Link;
import com.project.ws.repository.CustomerRepository;
import com.project.ws.representation.AbstractRepresentation;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;
import com.project.ws.representation.CustomerUpdateRequest;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.StringRepresentation;

@Component
@Transactional
@Service
public class CustomerActivity {

	private final CustomerRepository custRepo;
	
	@Autowired
	Customer newCustomer;
	
	@Autowired
	CustomerRepresentation customerRepresentation;
	
	private static final String baseUrl = "http://localhost:8080";
	
	@Autowired
	CustomerActivity(CustomerRepository custRepo) {
		this.custRepo = custRepo;
	}
	
	public CustomerRepresentation authenticateCustomer(String email, String password) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		Customer customer = custRepo.findByCustEmail(email);
		if(customer == null)
			return null;
		if(customer.getCustPassword().equals(password)) {
			customerRepresentation = mapRepresentation(customer);
			return customerRepresentation;
		}
		else
			return null;
	}
	
	public CustomerRepresentation addCustomer(CustomerRequest customerRequest) {
		newCustomer = mapRequest(customerRequest);
		Customer checkCustomer = custRepo.findByCustEmail(customerRequest.getEmail());
		if(checkCustomer != null) {
			CustomerRepresentation nullCustomerRepr = new CustomerRepresentation();
			nullCustomerRepr.setMessage("Customer Already Exists");
			return nullCustomerRepr;
		}
		custRepo.addCustomer(newCustomer.getCustFirstname(), newCustomer.getCustLastName(), newCustomer.getCustEmail(), newCustomer.getCustPassword());
		newCustomer = custRepo.findByCustFirstName(newCustomer.getCustFirstname());
		return mapRepresentation(newCustomer);
	}
	
	public StringRepresentation updateName(Integer customerId, String firstName, String lastName) {
		Integer count = custRepo.updateName(customerId, firstName, lastName);
		StringRepresentation stringRepresentation = new StringRepresentation();
		setLinks(stringRepresentation);
		if(count == 0) {
			stringRepresentation.setMessage("Error updating customer " + customerId);
		} else
			stringRepresentation.setMessage("Updated customer " + customerId + " 's name successfully to " + firstName + " " + lastName);
		return stringRepresentation;
	}

	public StringRepresentation updateEmail(Integer customerId, String email) {
		Integer count = custRepo.updateEmail(customerId, email);
		StringRepresentation stringRepresentation = new StringRepresentation();
		setLinks(stringRepresentation);
		if(count == 0)
			stringRepresentation.setMessage("Error updating customer " + customerId);
		else
			stringRepresentation.setMessage("Updated customer " + customerId + " 's email successfully to " + email);
		return stringRepresentation;
	}
	
	public StringRepresentation updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
		Integer count = custRepo.updateCustomer(customerUpdateRequest.getId(), customerUpdateRequest.getFirstName(), customerUpdateRequest.getLastName(), customerUpdateRequest.getEmail());
		StringRepresentation stringRepresentation = new StringRepresentation();
		setLinks(stringRepresentation);
		if(count == 0)
			stringRepresentation.setMessage("Error updating customer " + customerUpdateRequest.getId());
		else
			stringRepresentation.setMessage("Updated customer " + customerUpdateRequest.getId() + " 's name successfully to " + customerUpdateRequest.getFirstName() + " " + customerUpdateRequest.getLastName());
		return stringRepresentation;
	}

	public StringRepresentation deleteCustomer(Integer customerId) {
		Integer count = custRepo.deleteCustomer(customerId);
		StringRepresentation stringRepresentation = new StringRepresentation();
		setLinks(stringRepresentation);
		if(count == 0)
			stringRepresentation.setMessage("Error deleting Customer");
		else
			stringRepresentation.setMessage("Customer deleted Successfully");
		return stringRepresentation;
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
	
	public List<CustomerRepresentation> getAllCustomers() {
		List<Customer> customers = custRepo.findAll();
		if(customers == null) return null;
		List<CustomerRepresentation> customerRepresentations = new ArrayList<CustomerRepresentation>();
		for (Customer customer : customers) {
			customerRepresentations.add(mapRepresentation(customer));
		}
		return customerRepresentations;
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
		setLinks(customerRepresentation);
		return customerRepresentation;
	}
	
	private void setLinks(CustomerRepresentation customerRepresentation) {
		Link address = new Link("get", baseUrl + "/customeraddress/?customerId=", "address");
		Link billing = new Link("get", baseUrl + "/billing/?customerId=", "billing");
		Link orders = new Link("get", baseUrl + "/order?customerId=", "orders");
		Link cart = new Link("get", baseUrl + "/cart/view?customerId=", "cart");
		customerRepresentation.setLinks(address, billing, orders, cart);
	}
	
	private void setLinks(StringRepresentation stringRepresentation) {
		Link address = new Link("get", baseUrl + "/customeraddress/?customerId=", "address");
		Link billing = new Link("get", baseUrl + "/billing/?customerId=", "billing");
		Link orders = new Link("get", baseUrl + "/order?customerId=", "orders");
		Link cart = new Link("get", baseUrl + "/cart/view?customerId=", "cart");
		stringRepresentation.setLinks(address, billing, orders, cart);
	}

	
	//	private void setLinks(CustomerRepresentation customerRepresentation) {
//		Link cart = new Link("PUT", baseUrl + "/customer/updateCustomer/", "update email");
//		customerRepresentation.setLinks(cart);
//	}

}
