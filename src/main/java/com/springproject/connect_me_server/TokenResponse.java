package com.springproject.connect_me_server;

public class TokenResponse {
    private String token;
    private String channelName;
    private String uid;
    
    public TokenResponse() {}
    
    public TokenResponse(String token, String channelName, String uid) {
        this.token = token;
        this.channelName = channelName;
        this.uid = uid;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getChannelName() {
        return channelName;
    }
    
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
} 