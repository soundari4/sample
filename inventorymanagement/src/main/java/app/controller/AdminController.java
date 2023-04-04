package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.customexception.BusinessServiceException;
import app.customexception.ConstrainFailedException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.customexception.UserNotFoundException;
import app.model.Admin;
import app.pojo.Login;
import app.responsehandler.ResponseHandler;
import app.service.AdminService;
import app.stringconstant.StringConstant;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping(value = "/login")
	public ResponseEntity<Object> loginAdmin(@RequestBody @Valid Login admin)
			throws UserNotFoundException, BusinessServiceException {
		return ResponseHandler.processResponse(StringConstant.LOGIN_SUCCESS, HttpStatus.OK,
				adminService.getAdminByLoginDetails(admin));
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Object> saveAdmin(@Valid @RequestBody Admin admin)
			throws DuplicateException, BusinessServiceException, ConstrainFailedException {
		return ResponseHandler.processResponse(StringConstant.DATA_INSERTION_SUCCESS, HttpStatus.OK,
				adminService.saveAdmin(admin));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> changePersonalDetails(@PathVariable Long id, @Valid @RequestBody Admin admin)
			throws BusinessServiceException, NotFoundException {
		return ResponseHandler.processResponse(StringConstant.DATA_UPDATION_SUCCESS, HttpStatus.OK,
				adminService.updatePersonalDetails(admin, id));
	}

//	@GetMapping(value = "/all")
//	public ResponseEntity<Object> getDetails() throws BusinessServiceException {
//		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
//				adminService.getDetails());
//	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getAdminById(@PathVariable long id)
			throws BusinessServiceException, NotFoundException {
		System.out.println(id);
		return ResponseHandler.processResponse(StringConstant.DATA_RETRIVAL_SUCCESS, HttpStatus.OK,
				adminService.getById(id));
	}

}
