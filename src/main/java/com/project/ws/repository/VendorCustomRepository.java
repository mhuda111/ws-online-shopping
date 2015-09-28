package com.project.ws.repository;

import com.project.ws.domain.Vendor;

public interface VendorCustomRepository {
	
	public Integer addVendor(Vendor vendor);
	public Integer changeStatus(Integer vendorId, String flag);
	public Integer updateAddress(Vendor vendor);
	public Integer settleAccount(Integer vendorId, Double amount);
	public String notifyVendor(Integer vendorId);

}
