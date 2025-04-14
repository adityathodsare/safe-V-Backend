package com.safeVBackend.safeVBackend.repo;



import com.safeVBackend.safeVBackend.data.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}


