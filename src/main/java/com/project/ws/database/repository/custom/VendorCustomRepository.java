package com.project.ws.database.repository.custom;

import java.util.List;


import com.project.ws.database.domain.Vendor;

public interface VendorCustomRepository {
	
	public List<Vendor> getVendorByNamesFirstLetter(String letter);

}
