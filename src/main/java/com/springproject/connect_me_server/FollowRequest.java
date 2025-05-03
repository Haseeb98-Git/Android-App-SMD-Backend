package com.springproject.connect_me_server;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "follow_requests")
public class FollowRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String followerId;
    
    @Column(nullable = false)
    private String followedId;
    
    @Column(nullable = false)
    private LocalDateTime requestDate = LocalDateTime.now();
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
    
    public enum RequestStatus {
        PENDING, ACCEPTED, REJECTED
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
    
    public String getFollowedId() {
        return followedId;
    }
    
    public void setFollowedId(String followedId) {
        this.followedId = followedId;
    }
    
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    
    public RequestStatus getStatus() {
        return status;
    }
    
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
} 