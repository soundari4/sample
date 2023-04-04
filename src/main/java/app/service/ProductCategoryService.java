package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.model.ProductCategory;

@Service
public interface ProductCategoryService {

	ProductCategory addNewProductCategory(ProductCategory product) throws BusinessServiceException, DuplicateException;

	List<ProductCategory> getAllProductCategorys(String productCategoryStatus) throws BusinessServiceException, NotFoundException;

	ProductCategory deleteProductCategory(Long id) throws NotFoundException, BusinessServiceException;

	ProductCategory updateProductCategory(Long id, ProductCategory productCategory)
			throws NotFoundException, BusinessServiceException;

	ProductCategory getProductCategory(Long id) throws NotFoundException, BusinessServiceException;
}
