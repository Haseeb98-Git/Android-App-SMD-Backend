package com.springproject.connect_me_server;

public class FollowRequestDTO {
    private String followerId;
    private String followedId;
    
    // Getters and Setters
    public String getFollowerId() {
        return followerId;
    }
    
    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
    
    public String getFollowedId() {
        return followedId;
    }
    
    public void setFollowedId(String followedId) {
        this.followedId = followedId;
    }
} 