package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.NotFoundException;
import app.customexception.TransactionFailedException;
import app.model.Customer;
import app.model.Order;
import app.model.Transaction;
import app.pojo.OrderDetail;

@Service
public interface OrderService {

	List<Order> newOrder(List<Order> orders, Customer customer);

	List<Order> saveOrder(List<OrderDetail> orders, Customer customer, Transaction transaction)
			throws BusinessServiceException, TransactionFailedException;

	List<Order> getOrderDetailsBasedOnTransactionId(Long transactionId)
			throws BusinessServiceException, NotFoundException;

	List<Order> saveOrders(List<Order> orders) throws TransactionFailedException,BusinessServiceException, NotFoundException;

	List<Order> getOrderDetailsByCustomerId(Long customerId)throws BusinessServiceException,NotFoundException;

}
