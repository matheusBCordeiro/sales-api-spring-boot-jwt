package com.matheuscordeiro.salesapi.api.controller;

import com.matheuscordeiro.salesapi.api.ApiErrors;
import com.matheuscordeiro.salesapi.exception.BusinessException;
import com.matheuscordeiro.salesapi.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(BusinessException e) {
        String mensagemErro = e.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handleOrderNotFoundException(OrderNotFoundException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }
}