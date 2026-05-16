package br.com.erudio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectsNullException extends RuntimeException {

    public RequiredObjectsNullException() {
        super("NAO E PERMITIDO PERSISTIR UMA ENTIDADE COM VALOR NULO, IS NOT ALLOWED TO PERSIST A NULL OBJECT!");
    }
    public RequiredObjectsNullException(String message) {
        super(message);
    }
}
