package com.chatserver.chatserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatserver.chatserver.model.Message;
import com.chatserver.chatserver.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByID(Long messageID){
        return messageRepository.findById(messageID)
        .orElseThrow(() -> new RuntimeException("Message Not Found!"));
    }
    
}
