package com.ejercicio2.apis.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {

    private final BindingResult errors;

    public ValidationException(BindingResult errors) {
        this.errors = errors;
    }

    public List<String> getMessages() {
        return null;
    }

    public Boolean validationParams(){
        return null;
    }


    @Override
    public String getMessage() {
        return this.getMessages().toString();
    }





}