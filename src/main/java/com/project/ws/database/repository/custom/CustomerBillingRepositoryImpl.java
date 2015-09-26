package com.project.ws.database.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.project.ws.database.domain.CustomerBillingDetails;

public class CustomerBillingRepositoryImpl implements CustomerBillingCustomRepository {

	private Integer count;
	private String SQL;
	
	@PersistenceContext 
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Integer addCardDetails(CustomerBillingDetails customerBillingDetail) {
		SQL = "INSERT INTO customer_billing_details(cust_id, bill_addr_line1, bill_addr_line2, bill_city, " + 
				"bill_state, bill_zip_code, card_type, card_no, cvv, card_name, card_expiry) VALUES (" +
				customerBillingDetail.getCustomerId() + ", " + customerBillingDetail.getBillAddrLine1() + ", " +
				customerBillingDetail.getBillAddrLine2() + ", " + customerBillingDetail.getBillCity() + ", " +
				customerBillingDetail.getBillState() + ", " + customerBillingDetail.getBillZipCode() + ", " +
				customerBillingDetail.getCardType() + ", " + customerBillingDetail.getCardNo() + ", " +
				customerBillingDetail.getCVV() + ", " + customerBillingDetail.getCardName() + ", " + 
				customerBillingDetail.getCardExpiry() + ")";
		Query query = em.createNativeQuery(SQL);
		count = query.executeUpdate();
		return count;
	}
	
	@Override
	@Transactional
	public Integer chargeCard(Integer customerId, Integer billId, Double amount) {
		String SQL="update customer_billing_details set amount_charged = " + amount + 
				" where cust_id = " + customerId + " and cust_bill_id = " + billId ;
		count = em.createNativeQuery(SQL).executeUpdate();
		return count;
	}
}
