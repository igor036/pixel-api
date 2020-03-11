package br.com.linecode.shared.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.linecode.shared.excpetion.BadRequestException;

@Service
public class ValidatorService {
	
	@Autowired
    private Validator validator;
	
	
	public <T> void assertModel(T model) {
		Set<ConstraintViolation<T>> violacoes = validator.validate(model);
		if (!violacoes.isEmpty()) {
			String message = violacoes.stream().findFirst().get().getMessage();
			throw new BadRequestException(message);
		}
	}
}
