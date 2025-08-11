package com.chatserver.chatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatserver.chatserver.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
    
}
