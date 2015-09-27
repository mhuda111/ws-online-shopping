package com.project.ws.database.repository.custom;

import com.project.ws.database.domain.Vendor;

public interface VendorCustomRepository {
	
	public Integer addVendor(Vendor vendor);
	public Integer changeStatus(Integer vendorId, String flag);
	public Integer updateAddress(Vendor vendor);
	public Integer settleAccount(Integer vendorId, Double amount);
	public String notifyVendor(Integer vendorId);

}
