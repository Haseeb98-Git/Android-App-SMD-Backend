package com.springproject.connect_me_server;

// PostResponse.java
public class PostResponse {
    private Integer postId;
    private String userId;
    private String userName;

    private String profilePicture;
    private String mediaUrl;
    private String caption;

    // Constructors
    public PostResponse(Integer postId, String userId, String userName, String mediaUrl, String caption, String profilePicture) {
        this.postId = postId;
        this.userId = userId;
        this.userName = userName;
        this.mediaUrl = mediaUrl;
        this.caption = caption;
        this.profilePicture = profilePicture;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
