package com.springproject.connect_me_server;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    @Column(nullable = false, unique = true)
    private String email;
    private String userName;
    private String fullName;
    private String profilePicture;

    private String password;

    @ElementCollection
    @CollectionTable(name = "user_followers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_id")
    private Set<String> followers = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "user_following", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_id")
    private Set<String> following = new HashSet<>();

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getFollowers() {
        return followers;
    }
    
    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }
    
    public Set<String> getFollowing() {
        return following;
    }
    
    public void setFollowing(Set<String> following) {
        this.following = following;
    }
    
    // Helper methods
    public void addFollower(String followerId) {
        this.followers.add(followerId);
    }
    
    public void removeFollower(String followerId) {
        this.followers.remove(followerId);
    }
    
    public void addFollowing(String followingId) {
        this.following.add(followingId);
    }
    
    public void removeFollowing(String followingId) {
        this.following.remove(followingId);
    }
}