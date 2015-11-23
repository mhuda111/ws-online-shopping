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
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;
import com.project.ws.representation.CustomerUpdateRequest;
import com.project.ws.representation.ProductRepresentation;

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
	
	public String updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
		Integer count = custRepo.updateCustomer(customerUpdateRequest.getId(), customerUpdateRequest.getFirstName(), customerUpdateRequest.getLastName(), customerUpdateRequest.getEmail());
		if(count == 0)
			return "Error updating customer " + customerUpdateRequest.getId();
		else
			return "Updated customer " + customerUpdateRequest.getId() + " 's name successfully to " + customerUpdateRequest.getFirstName() + " " + customerUpdateRequest.getLastName();
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
		Link cart = new Link("PUT", baseUrl + "/customer/updateCustomer/", "update email");
		customerRepresentation.setLinks(cart);
	}

}
