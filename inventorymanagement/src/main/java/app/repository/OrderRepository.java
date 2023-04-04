package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.Customer;
import app.model.Order;
import app.model.Transaction;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("from Order o where o.transaction=:transaction")
	List<Order> findByTransactionId(@Param("transaction") Transaction transactionId);

	@Query("from Order o where o.customer=:customer")
	List<Order> getOrderDetailsByCustomerId(@Param("customer") Customer customerId);

}
