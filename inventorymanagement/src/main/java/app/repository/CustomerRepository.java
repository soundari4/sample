package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//	@Query(value = "select c.name,c.user_name,t.amount from customer c left join order_details o on c.id=o.customer_id "
//			+ "left join transaction t on c.id=t.customer_id where c.id=:id", nativeQuery = true)
//	Collection<List> getDetails(Long id);
	
	@Modifying
	@Transactional
	@Query("update Customer c set c.wallet=:wallet where c.phoneNumber=:username")
	int incrementWalletAmount(@Param("username") String username,@Param("wallet") double amount);

	@Query("from Customer c where c.phoneNumber=:username")
	Customer findByUserName(String username);

	@Query("select c.id from Customer c where c.phoneNumber=:username and c.passWord=:passWord")
	Customer getByUsernameAndPassword(@Param("username") String username, @Param("passWord") String passWord);

	
}
