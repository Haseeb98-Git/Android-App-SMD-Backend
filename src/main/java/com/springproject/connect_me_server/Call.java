package com.springproject.connect_me_server;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String callId;
    
    @Column(nullable = false)
    private String callerId;
    
    @Column(nullable = false)
    private String receiverId;
    
    @Column(nullable = false)
    private String callType;  // "voice" or "video"
    
    @Column(nullable = false)
    private String channelName;
    
    @Column(nullable = false)
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    @Column(nullable = false)
    private String status; // "off", "pending", "ongoing"
    
    @Column(nullable = false)
    private boolean active;
    
    // Constructors
    public Call() {
        this.startTime = LocalDateTime.now();
        this.status = "off";
        this.active = false;
    }
    
    public Call(String callerId, String receiverId, String callType, String channelName) {
        this.callerId = callerId;
        this.receiverId = receiverId;
        this.callType = callType;
        this.channelName = channelName;
        this.startTime = LocalDateTime.now();
        this.status = "pending";
        this.active = true;
    }
    
    // Getters and Setters
    public String getCallId() {
        return callId;
    }
    
    public void setCallId(String callId) {
        this.callId = callId;
    }
    
    public String getCallerId() {
        return callerId;
    }
    
    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }
    
    public String getReceiverId() {
        return receiverId;
    }
    
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    
    public String getCallType() {
        return callType;
    }
    
    public void setCallType(String callType) {
        this.callType = callType;
    }
    
    public String getChannelName() {
        return channelName;
    }
    
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    // Helper methods
    public void acceptCall() {
        this.status = "ongoing";
    }
    
    public void endCall() {
        this.endTime = LocalDateTime.now();
        this.status = "off";
        this.active = false;
    }
} 