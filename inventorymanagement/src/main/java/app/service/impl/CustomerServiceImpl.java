package app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.customexception.UserNotFoundException;
import app.model.Customer;
import app.pojo.Login;
import app.repository.CustomerRepository;
import app.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

//	@Override
//	public List<Customer> getAll() {
//		return customerRepository.findAll();
//	}

	@Override
	public Customer getById(Long id) throws NotFoundException, BusinessServiceException {
		try {
			return customerRepository.findById(id).get();
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public Customer saveCustomer(Customer customer) throws DuplicateException, BusinessServiceException {
		try {
			if (customer.getWallet() == 0)
				customer.setWallet(100);
			return customerRepository.save(customer);
		} catch (Exception e) {
			while (e != null) {
				if (e.getCause().getCause().getMessage().contains("Duplicate entry")
						&& e.getCause().getCause().getMessage().contains("UK_PhoneNumber"))
					throw new DuplicateException("Phone Number already exists");
			}
			throw new BusinessServiceException();
		}
	}

	@Override
	public Customer updateDetailsOfCustomer(Long id, Customer customer)
			throws NotFoundException, BusinessServiceException {
		try {
			Customer updateCustomer = customerRepository.findById(id).get();
			updateCustomer.setPassWord(customer.getPassWord());
			updateCustomer.setMailId(customer.getMailId());
			updateCustomer.setPassWord(customer.getPassWord());
			return customerRepository.save(updateCustomer);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public Long checkCustomerLoginDetails(Login customerLogin) throws UserNotFoundException, BusinessServiceException {
		try {
			Customer existingCustomer = customerRepository.getByUsernameAndPassword(customerLogin.getUsername(),
					customerLogin.getPassWord());
			if (existingCustomer == null)
				throw new UserNotFoundException();
			return existingCustomer.getId();

		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("No Customer is available with this Username and Password");
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public String increaseWalletAmount(String username, double amount)
			throws BusinessServiceException, UserNotFoundException {
		try {
			Customer customer = customerRepository.findByUserName(username);
			if (customer != null) {
				amount = customer.getWallet() + amount;
				if (customerRepository.incrementWalletAmount(username, amount) == 1) {
					return "increamented";
				}
			} else
				throw new NotFoundException();
		} catch (NotFoundException e) {
			throw new UserNotFoundException("No customer is available with this username");
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
		return null;
	}

}
