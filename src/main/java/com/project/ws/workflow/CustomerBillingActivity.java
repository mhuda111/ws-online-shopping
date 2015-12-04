package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.domain.Link;
import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.representation.BillingRequest;
import com.project.ws.representation.CustAddrRepresentation;
import com.project.ws.representation.CustBillingRepresentation;
import com.project.ws.representation.StringRepresentation;

@Component
@Transactional
@Service
public class CustomerBillingActivity {
	
	private static final String baseUrl = "http://localhost:8080";
	private static final String mediaType = "application/json";

	private final CustomerBillingRepository billRepo;
	
	@Autowired
	CustBillingRepresentation billRepresentation;

	@Autowired
	CustomerBillingDetails billingDetail;
	
	@Autowired
	CustomerBillingActivity(CustomerBillingRepository billRepo) {
		this.billRepo = billRepo;
	}
	
	public StringRepresentation addBillingDetails(BillingRequest billRequest) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		mapRequest(billRequest);
		Integer count = billRepo.addCardDetails(billingDetail);
		//Integer billId = billRepo.getBillId(billRequest.getCustomerId(), billRequest.getCardType());
		if(count == 1) {
			stringRepresentation.setMessage("Billing Details updated Successfully");
			setLinks(stringRepresentation, billRequest.getCustomerId());
			return stringRepresentation;	
		}
		else
			return null;
	}
	
	public StringRepresentation deleteBillingInfo(Integer billingId) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		billingDetail = new CustomerBillingDetails();
		billingDetail = billRepo.findOne(billingId);
		billRepo.delete(billingId);
		stringRepresentation.setMessage("Billing Details deleted successfully");
		setLinks(stringRepresentation, billingDetail.getCustomerId());
		return stringRepresentation;
	}
	
	public List<CustBillingRepresentation> getBillingDetails(Integer customerId) {
		List<CustomerBillingDetails> billingList = new ArrayList<CustomerBillingDetails>();
		List<CustBillingRepresentation> resultList = new ArrayList<CustBillingRepresentation>();
		billingList = billRepo.findByCustomerId(customerId);
		for(CustomerBillingDetails b:billingList) {
			resultList.add(mapRepresentation(b));
		}
		return resultList;
	}
	
	public StringRepresentation processPayment(Integer customerId, Integer billId, Double amount, String type) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer count = billRepo.updateAmount(customerId, billId, amount, type);
		if(count == null)
			return null;
		billingDetail = billRepo.findOne(billId);
		return stringRepresentation;
	}
	
	public CustomerBillingDetails mapRequest(BillingRequest billRequest) {
		billingDetail = new CustomerBillingDetails();
		billingDetail.setBillAddrLine1(billRequest.getBillAddrLine1());
		billingDetail.setBillAddrLine2(billRequest.getBillAddrLine2());
		billingDetail.setBillCity(billRequest.getBillCity());
		billingDetail.setBillState(billRequest.getBillState());
		billingDetail.setBillZipCode(billRequest.getBillZipCode());
		billingDetail.setCardExpiry(billRequest.getCardExpiry());
		billingDetail.setCardName(billRequest.getCardName());
		billingDetail.setCardNo(billRequest.getCardNo());
		billingDetail.setCardType(billRequest.getCardType());
		billingDetail.setCVV(billRequest.getCVV());
		billingDetail.setCustomerId(billRequest.getCustomerId());
		return billingDetail;
	}
	
	public CustBillingRepresentation mapRepresentation(CustomerBillingDetails billingDetail) {
		billRepresentation = new CustBillingRepresentation();
		billRepresentation.setBillAddrLine1(billingDetail.getBillAddrLine1());
		billRepresentation.setBillAddrLine2(billingDetail.getBillAddrLine2());
		billRepresentation.setBillCity(billingDetail.getBillCity());
		billRepresentation.setBillState(billingDetail.getBillState());
		billRepresentation.setBillZipCode(billingDetail.getBillZipCode());
		String cardNo = billingDetail.getCardNo();
		billRepresentation.setCardNo("************" + cardNo.substring(cardNo.length()-4, cardNo.length()));
		billRepresentation.setCardType(billingDetail.getCardType());
		billRepresentation.setCustomerId(billingDetail.getCustomerId());
		billRepresentation.setCustBillId(billingDetail.getCustBillId());
		setLinks(billRepresentation);
		return billRepresentation;
	}

	private void setLinks(StringRepresentation stringRepresentation, Integer customerId) {
		Link viewBilling = new Link("get", baseUrl + "/billing/?customerId=" + customerId, "viewBilling", mediaType);
		Link viewCustomer = new Link("get", baseUrl + "/customer/?customerId=" + customerId, "viewCustomer", mediaType);
		billRepresentation.setLinks(viewBilling, viewCustomer);
	}
	
	private void setLinks(CustBillingRepresentation billRepresentation) {
		Link addBilling = new Link("post", baseUrl + "/billing/", "addBilling", mediaType);
		Link deleteBilling = new Link("delete", baseUrl + "/billing/?billingId=" + billRepresentation.getCustBillId(), "deleteBilling", mediaType);
		Link selectPayment = new Link("put", baseUrl + "/cart/selectPayment?customerId=" + billRepresentation.getCustomerId() + "&billId=" + billRepresentation.getCustBillId(), "selectPayment", mediaType);
		billRepresentation.setLinks(addBilling, deleteBilling, selectPayment);
	}
}
