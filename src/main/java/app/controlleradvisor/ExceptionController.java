package app.controlleradvisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import app.customexception.BusinessServiceException;
import app.customexception.ConstrainFailedException;
import app.customexception.DuplicateException;
import app.customexception.NotFoundException;
import app.customexception.TransactionFailedException;
import app.customexception.UserNotFoundException;
import app.responsehandler.ResponseHandler;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(BusinessServiceException.class)
	public ResponseEntity<Object> handleBusinessServiceException(BusinessServiceException e) {
		return ResponseHandler.processExceptionResponse("Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleProductCategoryNotFoundException() {
		return ResponseHandler.processExceptionResponse("NO DATA AVAILABLE IN THAT ID",
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<Object> handleDuplicateException(DuplicateException e) {
		return ResponseHandler.processExceptionResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
		return ResponseHandler.processExceptionResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(TransactionFailedException.class)
	public ResponseEntity<Object> handleTransctionFailedException(TransactionFailedException e) {
		return ResponseHandler.processExceptionResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		if (e.getMessage().contains("phoneNumber"))
			return ResponseHandler.processExceptionResponse(
					"Invalid phone number must only numeric value and contains 10 digits",
					HttpStatus.UNPROCESSABLE_ENTITY);
		if(e.getMessage().contains("Email"))
			return ResponseHandler.processExceptionResponse(
					"Please enter a valid email Id",
					HttpStatus.UNPROCESSABLE_ENTITY);
		return ResponseHandler.processExceptionResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ConstrainFailedException.class)
	public ResponseEntity<Object> handleConstrainFailedException(ConstrainFailedException e) {
		return ResponseHandler.processExceptionResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
		return ResponseHandler.processExceptionResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
