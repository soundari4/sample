package app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	@Query("from ProductCategory p where p.deleted=false")
	List<ProductCategory> getPresentProductCategory();

	@Query("from ProductCategory p where p.deleted=true")
	List<ProductCategory> getDeletedProductCategory();

	@Modifying
	@Transactional
	@Query(value = ("update ProductCategory p set p.deleted=true,p.deletedOn=:deletedOn where p.id=:id"))
	void deleteProductCategory(@Param("id") Long id, @Param("deletedOn") Date date);

	@Modifying
	@Transactional
	@Query("update ProductCategory p set p.deleted=false, p.deletedOn=null where p.id=:id")
	int setDeletedToFalse(Long id);

	@Query("from ProductCategory p where p.categoryName=:categoryName")
	ProductCategory findByName(String categoryName);

}
