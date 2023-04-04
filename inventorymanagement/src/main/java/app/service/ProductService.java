package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.model.Product;
import app.model.ProductCategory;

@Service
public interface ProductService {

	Product saveNewProduct(Product productDetails) throws DuplicateException, BusinessServiceException;

	List<Product> getProducts(String productStatus) throws BusinessServiceException;

	Product deleteProduct(Long id) throws NotFoundException, BusinessServiceException;

	void changeCost(Product product, Long id);

	Product updateProduct(Product product, Long id) throws NotFoundException, BusinessServiceException;

	Product getProduct(Long id) throws BusinessServiceException, NotFoundException;

	List<Product> viewProductbyCategory(ProductCategory productCategoryId)
			throws BusinessServiceException, NotFoundException;

}
