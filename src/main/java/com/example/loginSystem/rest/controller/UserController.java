package com.example.loginSystem.rest.controller;
import com.example.loginSystem.domain.entities.Users;
import com.example.loginSystem.rest.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @GetMapping("/{id}")
    @Operation(summary = "returns a specific user by id")
    public ResponseEntity<Users>findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "add a new user")
    public ResponseEntity<Void>insert(@RequestBody @Valid Users obj){
        service.insert(obj);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update user data")
    public ResponseEntity<Void>update(@PathVariable Long id,@RequestBody Users obj){
        service.update(id,obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a user")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
