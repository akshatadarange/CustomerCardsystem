package com.barclays.cardsystem.service;

import java.util.List;

import com.barclays.cardsystem.dto.CardDTO;
import com.barclays.cardsystem.dto.CustomerDTO;
import com.barclays.cardsystem.exception.SystemException;

/**
 * Card Customer Service
 * @author Akshata
 *
 */
public interface CardCustomerService {
	public CustomerDTO getCustomerDetails(Integer customerId) throws SystemException;
	public Integer addCustomer(CustomerDTO customerDTO) throws SystemException;
	public void issueCardToExistingCustomer(Integer customerId, CardDTO cardDTO) throws SystemException;
	public void deleteCustomer(Integer customerId) throws SystemException;
	public void deleteCardOfExistingCustomer(Integer customerId, List<Integer> cardIdsToDelete) throws SystemException;	
		
}

