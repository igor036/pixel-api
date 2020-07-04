package br.com.linecode.config.excpection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.linecode.shared.excpetion.RestException;

@ControllerAdvice
public class ExceptionHandlerListener extends ResponseEntityExceptionHandler {
	
	@Autowired
	private Environment env;
	
	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		
		HttpHeaders headers = new HttpHeaders();
		
		
		if (ex instanceof RestException) {
			RestException restException = (RestException)ex;
			return handleExceptionInternal(ex, restException.getMessage(), headers, restException.getStatusCode() , request);
		}

		ex.printStackTrace();
		
		String unexpectedErrorMessage = env.getProperty("unexpected.error.message");
		return handleExceptionInternal(ex, unexpectedErrorMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
