package com.barclays.cardsystem.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.barclays.cardsystem.constants.SystemConstants;
import com.barclays.cardsystem.dto.CardDTO;
import com.barclays.cardsystem.dto.CustomerDTO;
import com.barclays.cardsystem.exception.SystemException;
import com.barclays.cardsystem.service.CardCustomerService;

/**
 * CustomerController - Customer Details API
 * @author Akshata
 * 
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	CardCustomerService cardCustomerService;
	
	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Integer customerId) throws SystemException {
		CustomerDTO customer = cardCustomerService.getCustomerDetails(customerId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Integer> addCustomer(@RequestBody CustomerDTO customer) throws SystemException {
		Integer id = cardCustomerService.addCustomer(customer);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
	@PostMapping("{customerId}/newcard")
	public ResponseEntity<String> issueCard(@PathVariable Integer customerId, @RequestBody CardDTO card) throws SystemException {
		cardCustomerService.issueCardToExistingCustomer(customerId, card);
		return new ResponseEntity<>(SystemConstants.CARD_ISSUSED_SUCCESS, HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws SystemException {
		cardCustomerService.deleteCustomer(customerId);
		return new ResponseEntity<>(SystemConstants.CUSTOMER_DELETE_SUCCESS, HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}/cards")
	public ResponseEntity<String> deleteCustomerCards(@PathVariable Integer customerId, @RequestBody List<Integer> cardIds) throws SystemException {
		cardCustomerService.deleteCardOfExistingCustomer(customerId, cardIds);
		return new ResponseEntity<>(SystemConstants.CARD_DELETE_SUCCESS, HttpStatus.OK);
	}
}
