package com.example.loginSystem.rest.service;

import com.example.loginSystem.domain.entities.Users;
import com.example.loginSystem.domain.repositories.UserRepository;
import com.example.loginSystem.rest.service.exceptions.CustomException;
import com.example.loginSystem.rest.service.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;


    public Users findByEmail(String email){

        Optional<Users> obj = repository.findByEmail(email);
        return obj.orElseThrow(() -> new ObjectNotFoundException("the requested user id does not exist"));
    }

    public Users findById(Long id){
        Optional<Users> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("the requested user id does not exist"));
    }

    public void insert(Users obj) {
        Optional<Users> user = repository.findByEmail(obj.getEmail());
        if(!user.isEmpty()){
            throw new CustomException("the email is already in use");
        }
        obj.setPass(encoder.encode(obj.getPass()));
        repository.save(obj);
    }

    public void update(Long id, Users obj) {
        try {
            Users newObj = repository.getReferenceById(id);
            obj.setPass(encoder.encode(obj.getPass()));
            updateData(newObj, obj);
            repository.save(newObj);
        }
        catch(EntityNotFoundException e){
            throw new ObjectNotFoundException("the requested user id does not exist");
        }
        catch (NullPointerException e){
            throw new ObjectNotFoundException("the requested user id does not exist");
        }
    }

    private void updateData(Users newObj, Users obj) {
        newObj.setPass(obj.getPass());
    }

    public void delete(Long id) {
        Optional<Users> user = repository.findById(id);
        if(user.isEmpty()){
            throw new ObjectNotFoundException("User not found. Or not exists");
        }
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = repository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        return user;
    }
}
