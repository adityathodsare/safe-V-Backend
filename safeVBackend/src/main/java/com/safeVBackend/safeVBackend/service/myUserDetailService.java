package com.safeVBackend.safeVBackend.service;

import com.safeVBackend.safeVBackend.data.User;

import com.safeVBackend.safeVBackend.data.userPrinciple;
import com.safeVBackend.safeVBackend.repo.userRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class myUserDetailService implements UserDetailsService {

    @Autowired
    private userRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);

        if(user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found with name : "+username);
        }
        return new userPrinciple(user);
    }
}
