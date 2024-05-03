package com.turkcell.authserver.service.concrete;

import com.turkcell.authserver.core.exception.type.UnauthorizedException;
import com.turkcell.authserver.entities.User;
import com.turkcell.authserver.service.abstracts.AuthService;
import com.turkcell.authserver.service.abstracts.UserService;
import com.turkcell.authserver.service.dtos.request.LoginRequest;
import com.turkcell.authserver.service.dtos.request.RegisterRequest;
import com.turkcell.authserver.service.mapper.AuthMapper;
import com.turkcell.tcell.core.security.BaseJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BaseJwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        User user = AuthMapper.INSTANCE.userFromRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.add(user);


    }

    @Override
    public String login(LoginRequest loginRequest) {
        //TODO: handle exception

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


            UserDetails user = userService.loadUserByUsername(loginRequest.getEmail());
            // TODO: Refactor
            Map<String, Object> claims = new HashMap<>();
            List<String> roles = user
                    .getAuthorities()
                    .stream()
                    .map((role) -> role.getAuthority())
                    .toList();
            claims.put("roles", roles);
            return jwtService.generateToken(loginRequest.getEmail(), claims);

        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Girdiğiniz kullanıcı adı veya şifre hatalı.");
        }
    }


}
