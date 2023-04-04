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
import app.model.ProductCategory;
import app.responsehandler.ResponseHandler;
import app.service.ProductCategoryService;
import app.stringconstant.StringConstant;

@RestController
@RequestMapping(value = "/productCategory")
public class ProductCategoryController {

	@Autowired
	ProductCategoryService productCategoryService;

	@PostMapping(value = "/save")
	public ResponseEntity<Object> newCategory(@RequestBody @Valid ProductCategory productCategory)
			throws BusinessServiceException, DuplicateException {
		return ResponseHandler.processResponse(StringConstant.DATA_INSERTION_SUCCESS, HttpStatus.OK,
				productCategoryService.addNewProductCategory(productCategory));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getProductCategory(@PathVariable Long id)
			throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				productCategoryService.getProductCategory(id));
	}

	@GetMapping(value = "/")
	public ResponseEntity<Object> getProductCategorys(@RequestParam("status") String productCategoryStatus)
			throws BusinessServiceException, NotFoundException {
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				productCategoryService.getAllProductCategorys(productCategoryStatus));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteProductCategory(@PathVariable Long id)
			throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_DELETION_SUCCESS, HttpStatus.OK,
				productCategoryService.deleteProductCategory(id));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateProductCategory(@PathVariable Long id,
			@RequestBody @Valid ProductCategory productCategory) throws NotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.DATA_UPDATION_SUCCESS, HttpStatus.OK,
				productCategoryService.updateProductCategory(id, productCategory));
	}

}
