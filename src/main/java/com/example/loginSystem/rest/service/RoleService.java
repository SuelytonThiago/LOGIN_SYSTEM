package com.example.loginSystem.rest.service;

import com.example.loginSystem.domain.entities.Roles;
import com.example.loginSystem.domain.entities.Users;
import com.example.loginSystem.domain.repositories.RoleRepository;
import com.example.loginSystem.domain.repositories.UserRepository;
import com.example.loginSystem.rest.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void enterAnewRoleForAUser(Long idUser, Long idRole){
        Users user = userRepository.findById(idUser)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        Roles role = roleRepository.findById(idRole)
                .orElseThrow(() -> new ObjectNotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }
}
