package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Vendor;
import com.project.ws.repository.custom.VendorCustomRepository;

public interface VendorRepository extends CrudRepository<Vendor, Integer>, VendorCustomRepository {

	    public Vendor findByVendorName(String vendorName);
	    @Override
		public List<Vendor> findAll();
	}


