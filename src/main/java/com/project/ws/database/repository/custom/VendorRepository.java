package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.project.ws.database.domain.Vendor;

public interface VendorRepository extends CrudRepository<Vendor, Long>, VendorCustomRepository {

	    public List<Vendor> findByVendorName(String vendorName);
	    public List<Vendor> findAll();
	}


