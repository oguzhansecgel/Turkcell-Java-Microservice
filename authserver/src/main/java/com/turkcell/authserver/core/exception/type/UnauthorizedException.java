package com.turkcell.authserver.core.exception.type;

import org.springframework.security.authentication.BadCredentialsException;

public class UnauthorizedException extends BadCredentialsException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
