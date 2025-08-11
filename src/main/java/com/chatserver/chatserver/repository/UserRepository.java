package com.chatserver.chatserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatserver.chatserver.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    
}
