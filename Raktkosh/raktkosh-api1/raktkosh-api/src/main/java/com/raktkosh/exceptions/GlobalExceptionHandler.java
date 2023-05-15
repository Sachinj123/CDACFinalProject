package com.raktkosh.exceptions;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.raktkosh.dto.ApiResponse;
import com.raktkosh.dto.ErrorResponse;

/**
 * Centralize Exception Handler.
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle exception thrown when request body has invalid data.
   */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("in handle method arg not valid !!!!!!!!!!!!!!!!!!");
		Map<String, String> collect = ex.getBindingResult().getFieldErrors().stream() // Stream<FieldError>
				.collect(Collectors.toMap(f -> f.getField(), FieldError::getDefaultMessage));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(collect);
	}
  
  /**
   * Handle exception thrown when request method is not valid.
   */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    
    StringBuilder message = new StringBuilder(ex.getMethod() + " method not allowed. ");
    message.append("Supported method(s) " + ex.getSupportedHttpMethods());
    
    return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new ErrorResponse(message.toString(), LocalDateTime.now()));
  }
  
  /**
   * Handle BloodRepositoryException thrown.
   */
  @ExceptionHandler(BloodRepositoryException.class)
  public ResponseEntity<?> handleBloodRepositoryException(BloodRepositoryException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
  
  @ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
		System.out.println("in handle res not found...");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
	}
  /**
   * Handle BloodRepositoryException thrown.
   */
  @ExceptionHandler(ActivationException.class)
  public ResponseEntity<?> handleActivationError(ActivationException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
  
  
  @ExceptionHandler(MessagingException.class)
	public ResponseEntity<?> handleRuntimeException(MessagingException e) {
		System.out.println("in handle run time exc");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Enter The Valid Email"));
	}
  
//  @ExceptionHandler(SMTPAddressFailedException.class)
//	public ResponseEntity<?> handleRuntimeException(SMTPAddressFailedException e) {
//		System.out.println("in handle run time exc");
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Enter The Valid Email"));
//	}
  @ExceptionHandler(RuntimeException.class)
 	public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
 		System.out.println("in handle run time exc");
 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
 	}
}
