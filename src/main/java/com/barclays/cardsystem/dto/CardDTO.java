package com.barclays.cardsystem.dto;

import java.time.LocalDate;

/**
 * CardDTO - Card data object 
 * @author Akshata
 */
public class CardDTO {
	Integer cardId;
	String cardNumber;
	LocalDate expiryDate;
	CustomerDTO customer;
	
	public Integer getCardId() {
		return cardId;
	}
	
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public CustomerDTO getCustomer() {
		return customer;
	}
	
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
}
