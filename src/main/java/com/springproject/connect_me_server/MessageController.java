package com.springproject.connect_me_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Send a message
    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        // Validate sender and receiver exist
        if (!userRepository.existsById(messageDTO.getSenderId()) || 
            !userRepository.existsById(messageDTO.getReceiverId())) {
            return ResponseEntity.badRequest().build();
        }
        
        Message message = new Message(
            messageDTO.getSenderId(),
            messageDTO.getReceiverId(),
            messageDTO.getContent()
        );
        
        message = messageRepository.save(message);
        return ResponseEntity.ok(MessageDTO.fromEntity(message));
    }
    
    // Get conversation between two users
    @GetMapping("/conversation")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @RequestParam String userId1,
            @RequestParam String userId2) {
        
        List<Message> messages = messageRepository.findConversation(userId1, userId2);
        List<MessageDTO> messageDTOs = messages.stream()
                .map(MessageDTO::fromEntity)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(messageDTOs);
    }
    
    // Get all conversations for a user
    @GetMapping("/user/{userId}/conversations")
    public ResponseEntity<List<String>> getUserConversations(@PathVariable String userId) {
        // This implementation is simplified and would need optimization in a real-world scenario
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.badRequest().build();
        }
        
        List<String> conversationPartners = messageRepository.findAll().stream()
                .filter(m -> m.getSenderId().equals(userId) || m.getReceiverId().equals(userId))
                .map(m -> m.getSenderId().equals(userId) ? m.getReceiverId() : m.getSenderId())
                .distinct()
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(conversationPartners);
    }
} 