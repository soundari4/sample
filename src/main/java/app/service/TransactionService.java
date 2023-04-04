package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.NotFoundException;
import app.model.Customer;
import app.model.Transaction;
import app.pojo.OrderDetail;

@Service
public interface TransactionService {

	//Transaction  saveTransactionDetails( List<Product> products, Customer customer);

	List<Transaction> getTransactionDetailsOfCustomer(Long customerId)throws NotFoundException,BusinessServiceException;

	Transaction saveTransactionDetails(List<OrderDetail> orders, Customer customer) throws BusinessServiceException;


}
