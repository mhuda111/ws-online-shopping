package com.project.ws.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.repository.custom.CustomerBillingCustomRepository;

public class CustomerBillingRepositoryImpl implements CustomerBillingCustomRepository {

	private Integer count;
	private String SQL;
	
	@PersistenceContext 
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Integer getBillId(Integer customerId, String cardType) {
		String SQL = "select custBillId from CustomerBillingDetails where customerId = " + customerId +
				" and lower(cardType) = '" + cardType.toLowerCase() + "'"; 
		Query query = em.createQuery(SQL);
		Integer billId = (Integer) query.getSingleResult();
		return billId;
	}
	
	@Override
	@Transactional
	public Integer addCardDetails(CustomerBillingDetails customerBillingDetail) {
		SQL = "INSERT INTO customer_billing_details(cust_id, bill_addr_line1, bill_addr_line2, bill_city, " + 
				"bill_state, bill_zip_code, card_type, card_no, cvv, card_name, card_expiry) VALUES (" +
				customerBillingDetail.getCustomerId() + ", '" + customerBillingDetail.getBillAddrLine1() + "', '" +
				customerBillingDetail.getBillAddrLine2() + "', '" + customerBillingDetail.getBillCity() + "', '" +
				customerBillingDetail.getBillState() + "', '" + customerBillingDetail.getBillZipCode() + "', '" +
				customerBillingDetail.getCardType() + "', '" + customerBillingDetail.getCardNo() + "', '" +
				customerBillingDetail.getCVV() + "', '" + customerBillingDetail.getCardName() + "', '" + 
				customerBillingDetail.getCardExpiry() + "')";
		count = em.createNativeQuery(SQL).executeUpdate();
		//count = query.executeUpdate();
		return count;
	}
	
	@Override
	@Transactional
	public Integer updateBillingAddress(Integer customerId, String addrLine1, String addrLine2, String city, String state, String zip) {
		String SQL="update customer_billing_details set bill_addr_line1 = '" + addrLine1 + "', bill_addr_line2 = '" +
				addrLine2 + "', bill_city = '" + city + "', bill_state = '" + state + "', bill_zip_code = '" + zip + "' where cust_id = " + 
				customerId;
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("billing address updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer updateAmount(Integer customerId, Integer billId, Double amount, String type) {
		Double currAmount = 0.00;
		Double newAmount = 0.00;
		CustomerBillingDetails billDetail = new CustomerBillingDetails();
		billDetail = em.find(CustomerBillingDetails.class, billId);
		currAmount = billDetail.getAmountCharged();
		if(type.equals("Debit"))
			newAmount = currAmount + amount;
		else if(type.equals("Credit"))
			newAmount = currAmount - amount;
		else
			return null;
		String SQL="update customer_billing_details set amount_charged = " + newAmount + 
				" where cust_id = " + customerId + " and cust_bill_id = " + billId ;
		count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("billing address updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
}
