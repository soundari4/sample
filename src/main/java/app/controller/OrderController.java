package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.customexception.BusinessServiceException;
import app.customexception.NotFoundException;
import app.customexception.TransactionFailedException;
import app.model.Order;
import app.responsehandler.ResponseHandler;
import app.service.OrderService;
import app.stringconstant.StringConstant;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	OrderService orderService;

//	@PostMapping(value = "/save")
//	public ResponseEntity<Object> saveOrder(@RequestBody List<OrderDetail> orders,
//			@Valid @RequestBody Customer customer, @RequestBody Transaction transaction)
//			throws BusinessServiceException, TransactionFailedException {
//		return ResponseHandler.processResponse(StringConstant.DATA_INSERTION_SUCCESS, HttpStatus.OK,
//				orderService.saveOrder(orders, customer, transaction));
//	}

	@PostMapping(value = "/save")
	public ResponseEntity<Object> saveOrders(@RequestBody List<Order> orders)
			throws TransactionFailedException, BusinessServiceException, NotFoundException {
		return ResponseHandler.processResponse(StringConstant.DATA_INSERTION_SUCCESS, HttpStatus.OK,
				orderService.saveOrders(orders));
	}

//	@GetMapping(value ="/customerTransaction")
//	public ResponseEntity<Object> getCustomerOrderByTransactionId(@RequestParam List<Long> transactionIds){
//		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK, or)
//	}

	@GetMapping(value = "/get/{transactionId}")
	public ResponseEntity<Object> getOrderDetailsBasedOnTransactionId(@PathVariable("transactionId") Long transactionId)
			throws BusinessServiceException, NotFoundException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				orderService.getOrderDetailsBasedOnTransactionId(transactionId));
	}

	@GetMapping(value = "/{customerId}")
	public ResponseEntity<Object> getOrderDetailsByCustomerId(@PathVariable("customerId") Long customerId)
			throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				orderService.getOrderDetailsByCustomerId(customerId));
	}

}
