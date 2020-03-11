package br.com.linecode.shared.excpetion;

import org.springframework.http.HttpStatus;

public class BadRequestException extends  RestException {

	private static final long serialVersionUID = 1L;
	
	public BadRequestException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
