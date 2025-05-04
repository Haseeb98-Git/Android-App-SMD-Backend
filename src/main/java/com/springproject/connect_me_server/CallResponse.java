package com.springproject.connect_me_server;

import java.time.LocalDateTime;

public class CallResponse {
    private String callId;
    private String channelName;
    private String token;
    private String callType;
    private String callerId;
    private String receiverId;
    private String callerName;
    private String receiverName;
    private String status;
    private boolean active;
    private LocalDateTime timestamp;
    
    public CallResponse() {}
    
    public CallResponse(
        String callId, 
        String channelName, 
        String token,
        String callType, 
        String callerId, 
        String receiverId,
        String callerName,
        String receiverName,
        String status,
        boolean active
    ) {
        this.callId = callId;
        this.channelName = channelName;
        this.token = token;
        this.callType = callType;
        this.callerId = callerId;
        this.receiverId = receiverId;
        this.callerName = callerName;
        this.receiverName = receiverName;
        this.status = status;
        this.active = active;
    }
    
    // Factory method to create from entity
    public static CallResponse fromEntity(Call call, String token) {
        return new CallResponse(
            call.getCallId(),
            call.getChannelName(),
            token,
            call.getCallType(),
            call.getCallerId(),
            call.getReceiverId(),
            "", // Caller name needs to be filled by controller
            "", // Receiver name needs to be filled by controller
            call.getStatus(),
            call.isActive()
        );
    }
    
    // Getters and Setters
    public String getCallId() {
        return callId;
    }
    
    public void setCallId(String callId) {
        this.callId = callId;
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
    
    public String getCallType() {
        return callType;
    }
    
    public void setCallType(String callType) {
        this.callType = callType;
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
    
    public String getCallerName() {
        return callerName;
    }
    
    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }
    
    public String getReceiverName() {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 