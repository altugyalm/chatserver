package com.chatserver.chatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatserver.chatserver.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
