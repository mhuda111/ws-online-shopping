package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.representation.BillingRequest;
import com.project.ws.representation.CustBillingRepresentation;

@Component
@Transactional
@Service
public class CustomerBillingActivity {

	private final CustomerBillingRepository billRepo;
	
	@Autowired
	CustBillingRepresentation billRepresentation;

	@Autowired
	CustomerBillingDetails billingDetail;
	
	@Autowired
	CustomerBillingActivity(CustomerBillingRepository billRepo) {
		this.billRepo = billRepo;
	}
	
	public CustBillingRepresentation addBillingDetails(BillingRequest billRequest) {
		mapRequest(billRequest);
		billRepo.addCardDetails(billingDetail);
		Integer billId = billRepo.getBillId(billRequest.getCustomerId(), billRequest.getCardType());
		billingDetail = new CustomerBillingDetails();
		billingDetail = billRepo.findOne(billId);
		return mapRepresentation(billingDetail);
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
	
	
	public CustBillingRepresentation processPayment(Integer customerId, Integer billId, Double amount, String type) {
		Integer count = billRepo.updateAmount(customerId, billId, amount, type);
		if(count == null)
			return null;
		billingDetail = billRepo.findOne(billId);
		return mapRepresentation(billingDetail);
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
		return billRepresentation;
	}
}
