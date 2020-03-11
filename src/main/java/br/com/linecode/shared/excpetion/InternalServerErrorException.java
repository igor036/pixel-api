package br.com.linecode.shared.excpetion;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RestException {

	private static final long serialVersionUID = 1L;
	
	public InternalServerErrorException(String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}
}
