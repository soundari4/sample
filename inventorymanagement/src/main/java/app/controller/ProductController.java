package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.customexception.BusinessServiceException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.model.Product;
import app.model.ProductCategory;
import app.responsehandler.ResponseHandler;
import app.service.ProductService;
import app.stringconstant.StringConstant;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping(value = "/save")
	public ResponseEntity<Object> newProduct(@RequestBody @Valid Product productDetails)
			throws DuplicateException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_INSERTION_SUCCESS, HttpStatus.OK,
				productService.saveNewProduct(productDetails));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody @Valid Product product)
			throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_UPDATION_SUCCESS, HttpStatus.OK,
				productService.updateProduct(product, id));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long id)
			throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_DELETION_SUCCESS, HttpStatus.OK,
				productService.deleteProduct(id));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable Long id) throws BusinessServiceException ,NotFoundException{
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				productService.getProduct(id));
	}

	@GetMapping(value = "/byCategory/{productCategoryId}")
	public ResponseEntity<Object> viewProductByCategory(@PathVariable ProductCategory productCategoryId)
			throws BusinessServiceException, NotFoundException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				productService.viewProductbyCategory(productCategoryId));
	}

	@GetMapping(value = "/")
	public ResponseEntity<Object> getProducts(@RequestParam("productStatus") String productStatus)
			throws BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				productService.getProducts(productStatus));
	}
}
