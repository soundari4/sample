package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.model.Customer;
import app.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("from Transaction t where t.customer=:customerId")
	List<Transaction> getTransactionDetailsByCustomerid(Customer customer);

}
