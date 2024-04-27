package com.turkcell.authserver.service.abstracts;

import com.turkcell.authserver.service.dtos.request.LoginRequest;
import com.turkcell.authserver.service.dtos.request.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);
    String login(LoginRequest loginRequest);
}
