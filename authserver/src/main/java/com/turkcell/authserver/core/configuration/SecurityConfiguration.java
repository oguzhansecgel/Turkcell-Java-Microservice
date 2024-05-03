package com.turkcell.authserver.core.configuration;

import com.turkcell.authserver.service.abstracts.UserService;
import com.turkcell.tcell.core.security.BaseJwtFilter;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;
    private final BaseJwtFilter jwtFilter;
    private static final String[] WHITE_LIST = {
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/index.html/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                 .authorizeHttpRequests((req)->
                          req
                                  .requestMatchers(WHITE_LIST).permitAll()
                                  .requestMatchers(HttpMethod.POST, "/api/v1/test/**").hasAnyAuthority("Test.Add")
                                  .requestMatchers(HttpMethod.PUT, "/api/v1/test/**").hasAnyAuthority("Test.Update")
                                  .requestMatchers(HttpMethod.DELETE, "/api/v1/test/**").hasAnyAuthority("Test.Delete")
                                  .anyRequest().authenticated()
                  )
                .csrf(AbstractHttpConfigurer::disable)  // Cross-Site Request Forgery
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
