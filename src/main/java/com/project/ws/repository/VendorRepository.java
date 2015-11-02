package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Vendor;
import com.project.ws.repository.custom.VendorCustomRepository;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Integer>, VendorCustomRepository {

	    public Vendor findByVendorName(String vendorName);
	    @Override
		public List<Vendor> findAll();
	}


