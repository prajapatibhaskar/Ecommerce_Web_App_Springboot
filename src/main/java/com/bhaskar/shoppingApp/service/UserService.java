package com.bhaskar.shoppingApp.service;

import com.bhaskar.shoppingApp.entity.Credential;
import com.bhaskar.shoppingApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean createUser(Credential credential){
        Credential user = userRepository.findByUsername(credential.getUsername());
        if(Objects.nonNull(user)){
            System.out.println("User exists");
            return false;
        }
        userRepository.save(credential);
        return true;
    }

    public boolean login(Credential credential){
        Credential user = userRepository.findByUsernameAndPassword(credential.getUsername(), credential.getPassword());
        if(Objects.nonNull(user)){
            return true;
        }
        return false;
    }
}
