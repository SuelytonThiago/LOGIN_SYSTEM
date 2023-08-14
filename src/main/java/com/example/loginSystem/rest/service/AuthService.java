package com.example.loginSystem.rest.service;

import com.example.loginSystem.config.security.TokenService;
import com.example.loginSystem.domain.entities.Users;
import com.example.loginSystem.domain.repositories.UserRepository;
import com.example.loginSystem.rest.dto.UserDto;
import com.example.loginSystem.rest.service.exceptions.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService jwtService;

    public Map<String,String> login(UserDto userDto){
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPass()));

        var user =(Users)authentication.getPrincipal();
        String access_token = jwtService.generateAccessToken(user);
        String refresh_token = jwtService.generateRefreshToken(user);

        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        return tokens;
    }

    public String attAccessToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        var token = authHeader.replace("Bearer ","");
        var email = jwtService.getSubject(token);
        var user = userRepository.findByEmail(email).get();

        if(jwtService.isTokenValid(token,user)){
            return jwtService.generateAccessToken(user);
        }
        throw new CustomException("invalid token");
    }
}
