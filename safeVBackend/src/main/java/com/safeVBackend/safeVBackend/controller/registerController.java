package com.safeVBackend.safeVBackend.controller;



import com.safeVBackend.safeVBackend.data.User;
import com.safeVBackend.safeVBackend.service.registerUserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class registerController {

    @Autowired
    private registerUserServiceimpl registerUserServiceimpl;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return registerUserServiceimpl.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user ){
//        System.out.println(user);
//        return("success");

        return registerUserServiceimpl.verify(user);
    }
}
