package com.turkcell.authserver.controllers;

import com.turkcell.authserver.entities.User;
import com.turkcell.authserver.service.abstracts.AuthService;
import com.turkcell.authserver.service.abstracts.UserService;
import com.turkcell.authserver.service.dtos.request.LoginRequest;
import com.turkcell.authserver.service.dtos.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request)
    {
        authService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginRequest request)
    {
        return authService.login(request);
    }

}
