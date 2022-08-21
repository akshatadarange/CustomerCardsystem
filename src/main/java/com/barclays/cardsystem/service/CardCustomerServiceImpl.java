package com.barclays.cardsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barclays.cardsystem.constants.SystemConstants;
import com.barclays.cardsystem.dto.CardDTO;
import com.barclays.cardsystem.dto.CustomerDTO;
import com.barclays.cardsystem.entity.Card;
import com.barclays.cardsystem.entity.Customer;
import com.barclays.cardsystem.exception.SystemException;
import com.barclays.cardsystem.repository.CardRepository;
import com.barclays.cardsystem.repository.CustomerRepository;

/**
 * Card Customer Service Implementation
 * @author Akshata
 *
 */
@Service(value = "cardCustomerService")
@Transactional
public class CardCustomerServiceImpl implements CardCustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CardRepository cardRepository;
	
	
	@Override
	public CustomerDTO getCustomerDetails(Integer customerId) throws SystemException {
		Optional<Customer> opt = customerRepository.findById(customerId);
		if (!opt.isPresent())
			throw new SystemException(SystemConstants.CUSTOMER_NOT_FOUND_RESPONSE);
		
		Customer customer = opt.get();
		
		List<Card> cards = cardRepository.findByCustomer_customerId(customerId);
		List<CardDTO> cardDTOs = new ArrayList<>();
		cards.forEach(card -> cardDTOs.add(convertCardToDTO(card, null)));
		
		CustomerDTO customerDTO = convertCustomerToDTO(customer, cardDTOs);
		
		return customerDTO;
	}

	
	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws SystemException {
		Customer customer = convertCustomerDTOtoModel(customerDTO);
		Customer newCustomer = customerRepository.save(customer);
		
		List<CardDTO> cardDTOs = customerDTO.getCards();
		if (cardDTOs != null && cardDTOs.size() > 0) {
			cardDTOs.forEach(cardDTO -> cardRepository.save(cardDTOtoModel(newCustomer, cardDTO)));
		}
		
		return newCustomer.getCustomerId();
	}

	
	@Override
	public void issueCardToExistingCustomer(Integer customerId, CardDTO cardDTO) throws SystemException {
		Optional<Customer> opt = customerRepository.findById(customerId);
		if (!opt.isPresent())
			throw new SystemException(SystemConstants.CUSTOMER_NOT_FOUND_RESPONSE);
		
		cardRepository.save(cardDTOtoModel(opt.get(), cardDTO));
	}

	
	@Override
	public void deleteCustomer(Integer customerId) throws SystemException {
		Optional<Customer> opt = customerRepository.findById(customerId);
		if (!opt.isPresent())
			throw new SystemException(SystemConstants.CUSTOMER_NOT_FOUND_RESPONSE);
		
		Customer customer = opt.get();
		List<Card> cards = cardRepository.findByCustomer_customerId(customerId);
		cards.forEach(card -> cardRepository.delete(card));
		customerRepository.delete(customer);
	}

	
	@Override
	public void deleteCardOfExistingCustomer(Integer customerId, List<Integer> cardIdsToDelete) throws SystemException {
		if (cardIdsToDelete == null || cardIdsToDelete.size() == 0) {
			throw new SystemException(SystemConstants.CARD_LIST_NOT_PROVIDED_RESPONSE);
		}
		
		Optional<Customer> opt = customerRepository.findById(customerId);
		if (!opt.isPresent())
			throw new SystemException(SystemConstants.CUSTOMER_NOT_FOUND_RESPONSE);
		
		Customer customer = opt.get();
		
		for (Integer cardId: cardIdsToDelete) {
			Optional<Card> cardOpt = cardRepository.findById(cardId);
			
			if (!cardOpt.isPresent())
				throw new SystemException(SystemConstants.CARD_NOT_FOUND_RESPONSE);
			
			Card card = cardOpt.get();
			if (card.getCustomer().getCustomerId() != customer.getCustomerId()) 
				throw new SystemException(SystemConstants.CARD_DOES_NOT_BELONG_TO_CUSTOMER_RESPONSE);
			
			cardRepository.delete(card);
		}
	}
	

	private Customer convertCustomerDTOtoModel(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setCustomerId(customerDTO.getId());
		customer.setName(customerDTO.getName());
		customer.setEmailid(customerDTO.getEmail());
		customer.setDateOfBirth(customerDTO.getDob());
		return customer;
	}
	
	private CustomerDTO convertCustomerToDTO(Customer customer, List<CardDTO> cardDTOs) {
		if (customer == null) return null;
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customer.getCustomerId());
		customerDTO.setEmail(customer.getEmailid());
		customerDTO.setName(customer.getName());
		customerDTO.setDob(customer.getDateOfBirth());
		customerDTO.setCards(cardDTOs);
		return customerDTO;
	}
	
	private Card cardDTOtoModel(Customer customer, CardDTO cardDTO) {
		Card card = new Card();
		card.setCardId(cardDTO.getCardId());
		card.setCardNumber(cardDTO.getCardNumber());
		card.setExpiryDate(cardDTO.getExpiryDate());
		card.setCustomer(customer);
		return card;
	}
	
	private CardDTO convertCardToDTO(Card card, Customer customer) {
		CardDTO cardDTO = new CardDTO();
		cardDTO.setCardId(card.getCardId());
		cardDTO.setCardNumber(card.getCardNumber());
		cardDTO.setExpiryDate(card.getExpiryDate());
		cardDTO.setCustomer(convertCustomerToDTO(customer, null));
		return cardDTO;
	}
}
