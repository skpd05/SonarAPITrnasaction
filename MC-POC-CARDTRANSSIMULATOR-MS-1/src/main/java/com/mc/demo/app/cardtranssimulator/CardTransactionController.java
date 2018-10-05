package com.mc.demo.app.cardtranssimulator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mc.demo.app.cardtranssimulator.exception.ObjectNotFoundException;
import com.mc.demo.app.cardtranssimulator.model.Cardtransaction;
import com.mc.demo.app.cardtranssimulator.service.CardTransactionService;

@RestController
@RequestMapping("/api/v1/simulate/card")
public class CardTransactionController {

	@Autowired
	CardTransactionService transService;

	@RequestMapping(value = "/gettransactions/{cardnumber}", method = RequestMethod.GET)
	public ResponseEntity<List<Cardtransaction>> getTransactionHistory(@PathVariable String cardnumber,
			@RequestHeader(name = "uuid", required = true) String uuid,
			@RequestHeader(name = "client_id", required = true) String clientId,
			@RequestHeader(name = "Accept", required = true) String accept) {
		List<Cardtransaction> transactions = transService.getTransactionHistory(cardnumber);
		if (transactions.size() == 0) {
			throw new ObjectNotFoundException("NO Record Found");
		}
		return new ResponseEntity<List<Cardtransaction>>(transactions, HttpStatus.OK);
	}

	@RequestMapping(value = "/savetransaction", method = RequestMethod.POST)
	public String saveTransaction(@RequestBody @Validated CreditCardTransaction transaction,
			@RequestHeader(name = "uuid", required = true) String uuid,
			@RequestHeader(name = "client_id", required = true) String clientId,
			@RequestHeader(name = "Accept", required = true) String accept) {
		transService.saveTransaction(transaction);
		return "Success";
	}

}
