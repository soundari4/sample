package app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.NotFoundException;
import app.customexception.TransactionFailedException;
import app.model.Customer;
import app.model.Order;
import app.model.Product;
import app.model.Transaction;
import app.pojo.OrderDetail;
import app.repository.CustomerRepository;
import app.repository.OrderRepository;
import app.repository.ProductRepository;
import app.repository.TransactionRepository;
import app.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public List<Order> newOrder(List<Order> orders, Customer customer) {
//		int amount = 0;
//
//		// check wallet amt is greater than the orders
//		// update wallet amt of customer
//
//		// transaction save
//		// add total amt and transaction date
//		// save orders
//		// save current product price
//		// less am -> throw exception
//
//		for (Order order : orders) {
//			amount = order.getProduct().getCost() * order.getProductQuantity();
//		}
//
//		Transaction transaction = new Transaction();
//		transaction.setCustomer(customer);
//		Transaction savedTransaction = new Transaction();
//		if (customer.getWallet() >= amount) {
//			transaction.setStatus("SUCCESS");
//			transaction.setAmount(amount);
//			savedTransaction = transactionRepository.save(transaction);
//			for (Order order : orders) {
//				order.setProductCost(order.getProduct().getCost());
//				order.setTransaction(savedTransaction);
//				orderRepository.save(order);
//			}
//			customer.setWallet(customer.getWallet() - amount);
//			customerRepository.save(customer);
//		} else {
//			// throws exception
//		}

		return orders;

	}

	@Override
	public List<Order> saveOrder(List<OrderDetail> orderDetails, Customer customer, Transaction transaction)
			throws BusinessServiceException, TransactionFailedException {
				return null;
//		Order order = new Order();
//		List<Order> savedOrders = new ArrayList<Order>();
//		try {
//			if (customer.equals(transaction.getCustomer())) {
//				if (transaction.getStatus().equals("SUCCESS")) {
//					order.setCustomer(customer);
//					order.setTransaction(transaction);
//					for (OrderDetail orderDetail : orderDetails) {
//						order.setProduct(orderDetail.getProduct());
//						order.setProductQuantity(order.getProductQuantity());
//						order.setProductCost(orderDetail.getProduct().getCost());
//						savedOrders.add(orderRepository.save(order));
//					}
//				} else
//					throw new TransactionFailedException(
//							"Your Transaction is failed due to lack of amount in the wallet. Please increase the wallet amount");
//			}
//		} catch (Exception e) {
//			throw new BusinessServiceException();
//		}
	}

	@Override
	public List<Order> getOrderDetailsBasedOnTransactionId(Long transactionId)
			throws NotFoundException, BusinessServiceException {
		 try {
		Transaction transaction = transactionRepository.findById(transactionId).get();
		List<Order> orders = orderRepository.findByTransactionId(transaction);
		if (orders.isEmpty())
			throw new NotFoundException();
		return orders;
		} catch (EmptyResultDataAccessException | NotFoundException |NoSuchElementException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public List<Order> saveOrders(List<Order> orders)
			throws TransactionFailedException, BusinessServiceException, NotFoundException {
		try {
			double amount = 0;
			int i = 0;
			Transaction transaction = new Transaction();
			Product product = new Product();
			List<Product> products = new ArrayList<Product>();

			Customer customer = customerRepository.findById(orders.get(0).getCustomer().getId()).get();
			for (Order order : orders) {
				product = productRepository.findById(order.getProduct().getId()).get();
				if (!product.isDeleted())
					products.add(product);
				else
					throw new NotFoundException();
				amount = product.getCost() * order.getProductQuantity();
			}
			transaction.setAmount(amount);
			transaction.setTransactionDate(new Date());
			transaction.setCustomer(customer);
			if (amount <= customer.getWallet() && amount != 0) {
				if (customerRepository.incrementWalletAmount(customer.getPhoneNumber(),
						customer.getWallet() - amount) == 1) {
					transaction.setStatus("SUCCESS");
					transaction = transactionRepository.save(transaction);
					for (Order order : orders) {
						order.setProductCost(products.get(i).getCost());
						order.setTransaction(transaction);
						order.setCustomer(customer);
						orderRepository.save(order);
						i++;
					}
				}
			} else {
				transaction.setStatus("FAILED");
				transaction.setReason("Due to less amount in wallet" + customer.getWallet());
				transactionRepository.save(transaction);
				throw new TransactionFailedException(
						"Transaction is failed due to the less amount in the wallet. Please increase the wallet amount"
								+ " Present wallet amount is " + customer.getWallet()
								+ " The Amount for the bought product is " + amount);
			}
		}  catch(TransactionFailedException e) {
			throw new TransactionFailedException(e.getMessage());
		}
		catch (NotFoundException e) {
			throw new NotFoundException();
		}  catch (Exception e) {
			throw new BusinessServiceException();
		}
		return orders;
	}

	@Override
	public List<Order> getOrderDetailsByCustomerId(Long customerId) throws BusinessServiceException, NotFoundException {
		try {
			Customer customer=customerRepository.findById(customerId).get();
			return orderRepository.getOrderDetailsByCustomerId(customer);
		} catch (NoSuchElementException  e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

}
