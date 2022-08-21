package com.barclays.cardsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.barclays.cardsystem.entity.Card;

/**
 * CardRepository Card Interface
 * @author Akshata
 *
 */
public interface CardRepository extends CrudRepository<Card, Integer> {
	List<Card> findByCustomer_customerId(Integer customerId);
}
