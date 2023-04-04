package app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.Product;
import app.model.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Transactional
	@Modifying
	@Query("update Product p set p.deleted = true,p.deletedOn=:date where p.id = :id")
	void softDeleteById(@Param("id") Long id, @Param("date") Date date);

	@Query(value = "from Product p where p.deleted = false")
	List<Product> getProductsForCustomer();

	@Query("from Product p where p.deleted=true")
	List<Product> getDeletedProducts();

	@Query(value = "from Product p where p.productCategory=:productCategoryId and p.deleted=false")
	List<Product> viewProductByCategory(Long productCategoryId);

	@Query(value = "from Product p where p.id=:id and p.deleted=false")
	Product getProductById(Long id);

	@Transactional
	@Modifying
	@Query("update Product p set p.deleted=false,p.deletedOn=null,p.cost=:cost,p.quantity=:quantity where p.id=:id")
	int setDeletedToFalse(@Param("id") Long id, @Param("cost") double cost, @Param("quantity") int quantity);

	@Query("from Product p where p.name=:name")
	Product findByName(String name);

	@Modifying
	@Transactional
	@Query("update Product p set p.deleted=true,p.deletedOn=:date where p.productCategory=:productCategory")
	int deleteProductByCategory(ProductCategory productCategory, Date date);

	boolean existsByName(String name);



}
