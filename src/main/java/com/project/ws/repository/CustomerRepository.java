package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public List<Customer> findByName(String name);
    //public Page<Customer> findAll(Pageable pageable);
}
