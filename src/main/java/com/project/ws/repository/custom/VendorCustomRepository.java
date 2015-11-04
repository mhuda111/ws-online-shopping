package com.project.ws.repository.custom;

import com.project.ws.domain.Vendor;

public interface VendorCustomRepository {
	
	public Integer addVendor(Vendor vendor);
	public Integer updateStatus(Integer vendorId, String flag);
	public Integer updateAddress(Vendor vendor);
	public Integer updateVendorName(Integer vendorId, String name);
	public Integer deleteVendor(Integer vendorId);
	public Integer updatePayment(Integer vendorId, Double amount);

}
