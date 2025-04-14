package com.safeVBackend.safeVBackend.service;


import com.safeVBackend.safeVBackend.data.User;
import com.safeVBackend.safeVBackend.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class registerUserServiceimpl {


    @Autowired
    private userRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User register(User user) {
       user.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));
       return  userRepo.save(user);
    }

//    public String verify(User user) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//       if (authentication.isAuthenticated()) {
//           return jwtService.generateToken(user.getUsername());
//       }
//       return "login failed / register first ";
//    }


    // first check to db then do further process
    public String verify(User user) {
        User existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser == null) {
            return "User not found. Please register first.";
        }

        // Manually compare raw password with stored hash
        if (!bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return "Invalid credentials";
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }

            return "Login failed.";
    }

}
