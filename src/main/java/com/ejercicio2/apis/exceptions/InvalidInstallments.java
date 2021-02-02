package com.ejercicio2.apis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidInstallments extends RuntimeException{

    public InvalidInstallments(String exception) {
        super(exception);
    }
}
