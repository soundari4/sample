package app.service;

import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.customexception.UserNotFoundException;
import app.model.Customer;
import app.pojo.Login;

@Service
public interface CustomerService {

	// List<Customer> getAll();

	Customer getById(Long id) throws BusinessServiceException, NotFoundException;

	Customer saveCustomer(Customer customer) throws DuplicateException, BusinessServiceException;

	Customer updateDetailsOfCustomer(Long id, Customer customer) throws NotFoundException, BusinessServiceException;

	Long checkCustomerLoginDetails(Login customerLogin) throws UserNotFoundException, BusinessServiceException;

	String increaseWalletAmount(String username, double amount) throws BusinessServiceException, UserNotFoundException;

}
