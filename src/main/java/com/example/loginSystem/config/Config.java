package com.example.loginSystem.config;

import com.example.loginSystem.domain.entities.Roles;
import com.example.loginSystem.domain.entities.Users;
import com.example.loginSystem.domain.enums.RoleName;
import com.example.loginSystem.domain.repositories.RoleRepository;
import com.example.loginSystem.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class Config implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public void run(String... args) throws Exception {
        Users user = new Users(null,"ana@example.com", encoder.encode("senha123"));
        Users adm = new Users(null, "adm@example.com",encoder.encode("senha123"));
        userRepository.saveAll(Arrays.asList(user,adm));

        Roles roles = new Roles(null,RoleName.ROLE_USER);
        Roles roles1 = new Roles(null, RoleName.ROLE_ADMIN);
        roleRepository.saveAll(Arrays.asList(roles,roles1));

        user.getRoles().add(roles);
        adm.getRoles().add(roles1);
        userRepository.saveAll(Arrays.asList(user,adm));


    }
}
