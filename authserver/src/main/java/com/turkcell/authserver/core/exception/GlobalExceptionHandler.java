package com.turkcell.authserver.core.exception;

import com.turkcell.authserver.core.exception.problemdetails.UnauthorizedExceptionDetails;
import com.turkcell.authserver.core.exception.type.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public UnauthorizedExceptionDetails handleRuntimeException(UnauthorizedException exception){
        UnauthorizedExceptionDetails unAuthorizedExceptionDetails = new UnauthorizedExceptionDetails();
        unAuthorizedExceptionDetails.setDetail(exception.getMessage());
        return unAuthorizedExceptionDetails;
    }
}
