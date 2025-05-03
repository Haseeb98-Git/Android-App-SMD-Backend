package com.springproject.connect_me_server;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime timestamp;
    
    public MessageDTO() {}
    
    public MessageDTO(Long id, String senderId, String receiverId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }
    
    // Factory method to create a DTO from an entity
    public static MessageDTO fromEntity(Message message) {
        return new MessageDTO(
            message.getId(),
            message.getSenderId(),
            message.getReceiverId(),
            message.getContent(),
            message.getTimestamp()
        );
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSenderId() {
        return senderId;
    }
    
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    
    public String getReceiverId() {
        return receiverId;
    }
    
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 