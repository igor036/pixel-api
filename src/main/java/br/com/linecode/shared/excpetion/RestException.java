package br.com.linecode.shared.excpetion;

import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus statusCode;
	private String message;
	
	public RestException(HttpStatus statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}
}
