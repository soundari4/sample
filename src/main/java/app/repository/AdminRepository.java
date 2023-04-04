package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	@Query("from Admin a where a.username=:username and a.passWord=:passWord")
	Admin findByUsernameAndPassword(@Param("username") String username, @Param("passWord") String passWord);

}
