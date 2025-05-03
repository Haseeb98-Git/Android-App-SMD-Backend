package com.springproject.connect_me_server;

import java.time.LocalDateTime;

public class FollowRequestResponse {
    private String id;
    private String followerId;
    private String followerName;
    private String followerProfilePicture;
    private String followedId;
    private String followedName;
    private LocalDateTime requestDate;
    private FollowRequest.RequestStatus status;
    
    // Constructors
    public FollowRequestResponse() {}
    
    public FollowRequestResponse(FollowRequest request, User follower, User followed) {
        this.id = request.getId();
        this.followerId = request.getFollowerId();
        this.followerName = follower.getUserName();
        this.followerProfilePicture = follower.getProfilePicture();
        this.followedId = request.getFollowedId();
        this.followedName = followed.getUserName();
        this.requestDate = request.getRequestDate();
        this.status = request.getStatus();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getFollowerId() {
        return followerId;
    }
    
    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
    
    public String getFollowerName() {
        return followerName;
    }
    
    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }
    
    public String getFollowerProfilePicture() {
        return followerProfilePicture;
    }
    
    public void setFollowerProfilePicture(String followerProfilePicture) {
        this.followerProfilePicture = followerProfilePicture;
    }
    
    public String getFollowedId() {
        return followedId;
    }
    
    public void setFollowedId(String followedId) {
        this.followedId = followedId;
    }
    
    public String getFollowedName() {
        return followedName;
    }
    
    public void setFollowedName(String followedName) {
        this.followedName = followedName;
    }
    
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    
    public FollowRequest.RequestStatus getStatus() {
        return status;
    }
    
    public void setStatus(FollowRequest.RequestStatus status) {
        this.status = status;
    }
} 