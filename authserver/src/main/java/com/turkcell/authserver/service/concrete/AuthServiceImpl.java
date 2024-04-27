package com.turkcell.authserver.service.concrete;

import com.turkcell.authserver.core.services.JwtService;
import com.turkcell.authserver.entities.User;
import com.turkcell.authserver.service.abstracts.AuthService;
import com.turkcell.authserver.service.abstracts.UserService;
import com.turkcell.authserver.service.dtos.request.LoginRequest;
import com.turkcell.authserver.service.dtos.request.RegisterRequest;
import com.turkcell.authserver.service.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {
        /*User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));*/
        // hasssas bilgiler veri tabanına plain text olarak kayıt edilmez
        User user = AuthMapper.INSTANCE.userFromRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.add(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if(!authentication.isAuthenticated())
            throw new RuntimeException("E-posta ya da şifre yanlış");


        Map<String, Object> claims = new HashMap<>();
        claims.put("UserId",1);
        claims.put("Deneme","Turkcell");
        return jwtService.generateToken(loginRequest.getEmail(),claims);


    }
}
