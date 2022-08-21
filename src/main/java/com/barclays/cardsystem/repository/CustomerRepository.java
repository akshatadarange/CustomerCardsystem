package com.barclays.cardsystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.barclays.cardsystem.entity.Customer;

/**
 * CustomerRepository - Customer Interface 
 * @author Akshata
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
}
