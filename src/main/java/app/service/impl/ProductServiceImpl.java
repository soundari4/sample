package app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.model.Product;
import app.model.ProductCategory;
import app.repository.ProductCategoryRepository;
import app.repository.ProductRepository;
import app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductCategoryRepository productCategoryRepository;

	public Product saveNewProduct(Product productDetails) throws DuplicateException, BusinessServiceException {
		try {
			Product existingProduct = productRepository.findByName(productDetails.getName());
			ProductCategory existsProductCategory = productCategoryRepository
					.findById(productDetails.getProductCategory().getId()).get();
			if (existsProductCategory != null && !existsProductCategory.isDeleted()) {
				if (existingProduct != null) {
					if (existingProduct.isDeleted()) {
						if (productRepository.setDeletedToFalse(existingProduct.getId(), productDetails.getCost(),
								productDetails.getQuantity()) == 1)
							return productRepository.findById(existingProduct.getId()).get();
					}
				}
				return productRepository.save(productDetails);
			}
		} catch (Exception e) {
			while (e != null) {
				if (e.getCause().getCause().getMessage().contains("Duplicate entry")
						&& e.getCause().getCause().getMessage().contains("UK_ProductName"))
					throw new DuplicateException("Product Name is already exists");
				else
					throw new BusinessServiceException();
			}
		}
		return productDetails;
	}

	@Override
	public List<Product> getProducts(String productStatus) throws BusinessServiceException {
		try {
			if (productStatus.equals("not deleted"))
				return productRepository.findAll();
			else if (productStatus.equals("deleted"))
				return productRepository.getDeletedProducts();
			else
				return productRepository.findAll();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public Product deleteProduct(Long id) throws NotFoundException, BusinessServiceException {

		try {
			Product product = productRepository.findById(id).get();
			if (product.isDeleted())
				throw new NotFoundException();
			else
				productRepository.softDeleteById(id, new Date());
		} catch (NotFoundException | EmptyResultDataAccessException | NoSuchElementException e) {
			throw new NotFoundException("No product is found with this ID");
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
		return null;
	}

	@Override
	public void changeCost(Product product, Long id) {
		Product updateproduct = productRepository.findById(id).orElse(null);
		updateproduct.setCost(product.getCost());
		productRepository.save(updateproduct);
	}

	@Override
	public Product updateProduct(Product product, Long id) throws NotFoundException, BusinessServiceException {
		try {
			Product updateproduct = productRepository.findById(id).get();
			if (updateproduct.isDeleted())
				throw new NotFoundException();
			else {
				updateproduct.setCost(product.getCost());
				updateproduct.setQuantity(product.getQuantity());
				updateproduct.setModifiedOn(new Date());
				updateproduct.setName(product.getName());
				return productRepository.save(updateproduct);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException();
		} catch (NotFoundException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

	@Override
	public Product getProduct(Long id) throws BusinessServiceException, NotFoundException {
		Product product = new Product();
		try {
			product = productRepository.findById(id).get();
			if (!product.isDeleted())
				return product;
			else
				throw new NotFoundException();
		} catch (NotFoundException |NoSuchElementException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}

	}

	@Override
	public List<Product> viewProductbyCategory(ProductCategory productCategoryId)
			throws BusinessServiceException, NotFoundException {
		try {
			return productRepository.viewProductByCategory(productCategoryId.getId());
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException();
		} catch (Exception e) {
			throw new BusinessServiceException();
		}
	}

}
