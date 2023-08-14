package com.example.loginSystem.rest.controller;

import com.example.loginSystem.rest.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/{idUser}/{idRole}")
    @Operation(summary = "add a role to a user")
    public ResponseEntity<Void> enterAnewRoleForAUser(@PathVariable Long idUser,@PathVariable Long idRole){
        roleService.enterAnewRoleForAUser(idUser,idRole);
        return ResponseEntity.noContent().build();
    }

}
