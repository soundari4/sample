package app.responsehandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

	public static ResponseEntity<Object> processResponse(String message, HttpStatus status, Object responseObj) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("status", status.value());
		response.put("data", responseObj);
		return new ResponseEntity<Object>(response,status);
	}

	public static ResponseEntity<Object> processExceptionResponse(String message, HttpStatus status){
		Map<String,Object> response=new HashMap<String,Object>();
		response.put("message", message);
		response.put("status",status.value());
		return new ResponseEntity<Object>(response,status);
	}
}
