package com.bhaskar.shoppingApp.controllers;

import com.bhaskar.shoppingApp.entity.Credential;
import com.bhaskar.shoppingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public boolean login(@RequestBody Credential credential){
        return userService.login(credential);
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody Credential credential){
        return userService.createUser(credential);
    }
}
