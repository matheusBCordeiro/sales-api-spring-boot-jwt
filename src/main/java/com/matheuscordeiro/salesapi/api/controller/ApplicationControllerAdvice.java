package com.matheuscordeiro.salesapi.api.controller;

import com.matheuscordeiro.salesapi.api.ApiErrors;
import com.matheuscordeiro.salesapi.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(BusinessException e){
        String mensagemErro = e.getMessage();
        return new ApiErrors(mensagemErro);
    }
}