package com.chatserver.chatserver.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.chatserver.chatserver.model.Message;
import com.chatserver.chatserver.service.MessageService;



@RestController
@RequestMapping("/api/messages")
public class MessageController {

     @Autowired
    private MessageService messageService;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    @GetMapping
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public Message getMessageByID(@PathVariable Long id) {
        return messageService.getMessageByID(id);
    }

    

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        Message saved = messageService.saveMessage(message);

        // Send to all active emitters
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(saved));
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }

        return saved;
    }

    @GetMapping("/stream")
    public SseEmitter streamMessages() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }
    
}
