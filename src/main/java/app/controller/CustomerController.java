package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.customexception.UserNotFoundException;
import app.model.Customer;
import app.pojo.Login;
import app.responsehandler.ResponseHandler;
import app.service.CustomerService;
import app.stringconstant.StringConstant;

@RestController
@RequestMapping(value = "/customer")

public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping(value = "/login")
	public ResponseEntity<Object> loginCustomer(@RequestBody @Valid Login customerLogin)
			throws UserNotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.LOGIN_SUCCESS, HttpStatus.OK,
				customerService.checkCustomerLoginDetails(customerLogin));
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Object> newCustomer(@RequestBody @Valid Customer customer)
			throws BusinessServiceException, DuplicateException {
		return ResponseHandler.processResponse(StringConstant.DATA_INSERTION_SUCCESS, HttpStatus.OK,
				customerService.saveCustomer(customer));
	}

//	@GetMapping(value = "/details")
//	public ResponseEntity<Object> getAllCustomer() {
//		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
//				customerService.getAll());
//	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getDetailsById(@PathVariable Long id)
			throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				customerService.getById(id));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateDetails(@PathVariable Long id, @RequestBody Customer customer)
			throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_UPDATION_SUCCESS, HttpStatus.OK,
				customerService.updateDetailsOfCustomer(id, customer));
	}

	@PutMapping(value = "/wallet")
	public ResponseEntity<Object> changeWalletAmount(@RequestParam(value = "username") String username,
			@RequestParam(value = "amount") int amount) throws BusinessServiceException, UserNotFoundException {
		return ResponseHandler.processResponse(StringConstant.DATA_UPDATION_SUCCESS, HttpStatus.OK,
				customerService.increaseWalletAmount(username, amount));

	}
}
