package com.turkcell.authserver.service.abstracts;

import com.turkcell.authserver.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void add(User user);

}
