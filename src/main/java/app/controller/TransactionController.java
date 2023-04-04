package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.customexception.BusinessServiceException;
import app.customexception.NotFoundException;
import app.responsehandler.ResponseHandler;
import app.service.TransactionService;
import app.stringconstant.StringConstant;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

//	@PostMapping(value = "/save")
//	public ResponseEntity<Object> saveTransaction(@RequestBody @Valid List<Product> products,
//			@RequestBody @Valid Customer customer) {
//		return ResponseHandler.processResponse(StringConstant.DATA_INSERTION_SUCCESS, HttpStatus.OK,
//				transactionService.saveTransactionDetails(products, customer));
//	}

	@GetMapping(value = "/{customerId}")
	public ResponseEntity<Object> getTransactionDetails(@PathVariable Long customerId)
			throws BusinessServiceException, NotFoundException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				transactionService.getTransactionDetailsOfCustomer(customerId));
	}

}
