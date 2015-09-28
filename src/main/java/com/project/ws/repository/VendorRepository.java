package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Vendor;

public interface VendorRepository extends CrudRepository<Vendor, Long>, VendorCustomRepository {

	    public List<Vendor> findByVendorName(String vendorName);
	    @Override
		public List<Vendor> findAll();
	}


