package app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.NotFoundException;
import app.model.Customer;
import app.model.Transaction;
import app.pojo.OrderDetail;
import app.repository.CustomerRepository;
import app.repository.TransactionRepository;
import app.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	CustomerRepository customerRepository;

//	@Override
//	public Transaction newTransaction(Transaction transaction, ) {
//		return transactionRepository.save(transaction);
//	}

	@Override
	public List<Transaction> getTransactionDetailsOfCustomer(Long customerId)
			throws NotFoundException, BusinessServiceException {
		// return (List<Transaction>) transactionRepository.findById(customerId).get();
		try {
			Customer customer = customerRepository.findById(customerId).get();
			List<Transaction> transactionDetailsOfCustomer = transactionRepository
					.getTransactionDetailsByCustomerid(customer);
			if (transactionDetailsOfCustomer != null) {
				return transactionDetailsOfCustomer;
			} else {
				throw new NotFoundException();
			}
		} catch (NoSuchElementException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

//	@Override
//	public Transaction saveTransactionDetails(List<Product> products, Customer customer) {
//		Transaction transaction = new Transaction();
//		int i = 0, amount = 0;
//		List<Integer> productQuantity = new ArrayList<Integer>();
//		for (Order order : orders) {
//			products.add(order.getProduct());
//			productQuantity.add(order.getProductQuantity());
//		}
//		for (Product product : products) {
//			amount = productQuantity.get(i) * product.getCost();
//			i++;
//		}
//		transaction.setCustomer(orders.get(0).getCustomer());
//		transaction.setAmount(amount);
//		
//		for(Product product:products) {
//			amount=product.getCost()*
//		}
//		
//		if (orders.get(0).getCustomer().getWallet() < amount)
//			transaction.setStatus("failed");
//		else
//			transaction.setStatus("success");
//		return transactionRepository.save(transaction);
//	}

	@Override
	public Transaction saveTransactionDetails(List<OrderDetail> orders, Customer customer)
			throws BusinessServiceException {
		try {
			double amount = 0;
			Transaction transaction = new Transaction();
			for (OrderDetail order : orders) {
				amount = order.getProduct().getCost() * order.getProductQuantity();
			}
			transaction.setAmount(amount);
			transaction.setCustomer(customer);
			transaction.setTransactionDate(new Date());
			if (amount <= customer.getWallet() && amount != 0)
				transaction.setStatus("SUCCESS");
			else
				transaction.setStatus("FAILED");
			return transactionRepository.save(transaction);
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

}