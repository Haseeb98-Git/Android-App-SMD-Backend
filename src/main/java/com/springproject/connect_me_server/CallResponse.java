package com.springproject.connect_me_server;

import java.time.LocalDateTime;

public class CallResponse {
    private String callId;
    private String callerId;
    private String receiverId;
    private String callType;
    private String channelName;
    private String token;
    private LocalDateTime timestamp;
    
    public CallResponse() {}
    
    public CallResponse(String callId, String callerId, String receiverId, String callType, 
                       String channelName, String token, LocalDateTime timestamp) {
        this.callId = callId;
        this.callerId = callerId;
        this.receiverId = receiverId;
        this.callType = callType;
        this.channelName = channelName;
        this.token = token;
        this.timestamp = timestamp;
    }
    
    // Factory method to create from entity
    public static CallResponse fromEntity(Call call, String token) {
        return new CallResponse(
            call.getCallId(),
            call.getCallerId(),
            call.getReceiverId(),
            call.getCallType(),
            call.getChannelName(),
            token,
            call.getStartTime()
        );
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
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 