package app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.model.ProductCategory;
import app.repository.ProductCategoryRepository;
import app.repository.ProductRepository;
import app.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public ProductCategory addNewProductCategory(ProductCategory product)
			throws BusinessServiceException, DuplicateException {
		try {
			ProductCategory existingProductCategory = productCategoryRepository.findByName(product.getCategoryName());
			if (existingProductCategory != null) {
				if (existingProductCategory.isDeleted()) {
					if (productCategoryRepository.setDeletedToFalse(existingProductCategory.getId()) == 1) {
						return productCategoryRepository.findById(existingProductCategory.getId()).get();
					}
				}
			}
			String randomString = UUID.randomUUID().toString().substring(0, 4);
			product.setCode(randomString);
			String code = product.getCategoryName().charAt(0) + randomString
					+ product.getCategoryName().charAt(product.getCategoryName().length() - 1);
			product.setCode(code);
		return productCategoryRepository.save(product);
		} catch (Exception e) {
			while (e != null) {
				if (e.getCause().getCause().getMessage().contains("Duplicate entry")
						&& e.getCause().getCause().getMessage().contains("UK_CategoryName"))
					throw new DuplicateException("This Product Category is already exists");
			}
			throw new BusinessServiceException();
		}
	}

	@Override
	public List<ProductCategory> getAllProductCategorys(String productCategoryStatus) throws BusinessServiceException, NotFoundException {
		try {
			if (productCategoryStatus.equals("not deleted"))
				return productCategoryRepository.getPresentProductCategory();
			else if (productCategoryStatus.equals("deleted"))
				return productCategoryRepository.getDeletedProductCategory();
			else
				return productCategoryRepository.findAll();
		}catch(NoSuchElementException e) {
			throw new NotFoundException();
		}
		catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public ProductCategory deleteProductCategory(Long id) throws NotFoundException, BusinessServiceException {
		ProductCategory productCategory = new ProductCategory();
		try {
			productCategory = productCategoryRepository.findById(id).get();
			if (productCategory.isDeleted())
				throw new NotFoundException();
			productCategoryRepository.deleteProductCategory(id, new Date());
			productRepository.deleteProductByCategory(productCategory,new Date());
		} catch (NotFoundException e) {
			throw new NotFoundException();
		} catch (EmptyResultDataAccessException | NoSuchElementException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
		return null;
	}

	@Override
	public ProductCategory updateProductCategory(Long id, @Valid ProductCategory productCategory)
			throws NotFoundException, BusinessServiceException {
		try {
			ProductCategory updateProductCategory = productCategoryRepository.findById(id).get();
			updateProductCategory.setCategoryName(productCategory.getCategoryName());
			return productCategoryRepository.save(updateProductCategory);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public ProductCategory getProductCategory(Long id) throws NotFoundException, BusinessServiceException {
		ProductCategory productCategory = new ProductCategory();
		try {
			productCategory = productCategoryRepository.findById(id).get();
			if (productCategory.isDeleted())
				throw new NotFoundException();
			else
				return productCategory;
		} catch (NotFoundException e) {
			throw new NotFoundException();
		} catch (NoSuchElementException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}
}
