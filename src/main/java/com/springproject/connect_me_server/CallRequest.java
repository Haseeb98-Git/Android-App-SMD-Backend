package com.springproject.connect_me_server;

public class CallRequest {
    private String callerId;
    private String receiverId;
    private String callType;
    private String channelName;
    
    public CallRequest() {}
    
    public CallRequest(String callerId, String receiverId, String callType, String channelName) {
        this.callerId = callerId;
        this.receiverId = receiverId;
        this.callType = callType;
        this.channelName = channelName;
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
} 