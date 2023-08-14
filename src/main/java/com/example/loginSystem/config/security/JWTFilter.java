package com.example.loginSystem.config.security;

import com.example.loginSystem.domain.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token;
        var authHeader =request.getHeader("Authorization");
        if(authHeader != null){
            token = authHeader.replace("Bearer ", "");
            var subject = tokenService.getSubject(token);
            var user = userRepository.findByEmail(subject).get();
            var authorization = new UsernamePasswordAuthenticationToken( user , null , user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authorization);
        }
        filterChain.doFilter(request,response);
    }
}
