package com.springproject.connect_me_server;

// PostResponse.java
public class StoryResponse {
    private Integer storyId;
    private String userId;
    private String userName;

    private String profilePicture;
    private String mediaUrl;

    // Constructors
    public StoryResponse(Integer storyId, String userId, String userName, String mediaUrl, String profilePicture) {
        this.storyId = storyId;
        this.userId = userId;
        this.userName = userName;
        this.mediaUrl = mediaUrl;
        this.profilePicture = profilePicture;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
