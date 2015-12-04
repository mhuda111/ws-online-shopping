package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Customer;
import com.project.ws.domain.Link;
import com.project.ws.repository.CustomerRepository;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;
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
	private static final String mediaType = "application/json";
	
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
		setLinks(stringRepresentation, customerId);
		if(count == 0) {
			stringRepresentation.setMessage("Error updating customer " + customerId);
		} else
			stringRepresentation.setMessage("Updated customer " + customerId + " 's name successfully to " + firstName + " " + lastName);
		return stringRepresentation;
	}

	public StringRepresentation updateEmail(Integer customerId, String email) {
		Integer count = custRepo.updateEmail(customerId, email);
		StringRepresentation stringRepresentation = new StringRepresentation();
		setLinks(stringRepresentation, customerId);
		if(count == 0)
			stringRepresentation.setMessage("Error updating customer " + customerId);
		else
			stringRepresentation.setMessage("Updated customer " + customerId + " 's email successfully to " + email);
		return stringRepresentation;
	}
	
	public StringRepresentation updateCustomer(CustomerRequest customerRequest) {
		Integer count = custRepo.updateCustomer(customerRequest.getCustomerId(), customerRequest.getFirstName(), customerRequest.getLastName(), customerRequest.getEmail());
		StringRepresentation stringRepresentation = new StringRepresentation();
		setLinks(stringRepresentation, customerRequest.getCustomerId());
		if(count == 0)
			stringRepresentation.setMessage("Error updating customer " + customerRequest.getCustomerId());
		else
			stringRepresentation.setMessage("Updated customer " + customerRequest.getCustomerId() + " 's name successfully to " + customerRequest.getFirstName() + " " + customerRequest.getLastName());
		return stringRepresentation;
	}

	public StringRepresentation deleteCustomer(Integer customerId) {
		Integer count = custRepo.deleteCustomer(customerId);
		StringRepresentation stringRepresentation = new StringRepresentation();
		setLinks(stringRepresentation, customerId);
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
		Link updateCustomer = new Link("put", baseUrl + "/customer/updateCustomer", "updateCustomer", mediaType);
		Link viewAddress = new Link("get", baseUrl + "/customeraddress/?customerId=" + customerRepresentation.getCustId(), "viewAddress", mediaType);
		Link viewBilling = new Link("get", baseUrl + "/billing/?customerId=" + customerRepresentation.getCustId(), "viewBilling", mediaType);
		Link viewOrders = new Link("get", baseUrl + "/order?customerId=" + customerRepresentation.getCustId(), "viewOrders", mediaType);
		Link viewCart = new Link("get", baseUrl + "/cart/view?customerId=" + customerRepresentation.getCustId(), "viewCart", mediaType);
		Link showAll = new Link("get", baseUrl + "/products", "showAll", mediaType);
		customerRepresentation.setLinks(updateCustomer, viewAddress, viewBilling, viewOrders, viewCart, showAll);
	}
	
	private void setLinks(StringRepresentation stringRepresentation, Integer customerId) {
		Link address = new Link("get", baseUrl + "/customer/?customerId=" + customerId, "viewCustomer", mediaType);
		stringRepresentation.setLinks(address);
	}

}
