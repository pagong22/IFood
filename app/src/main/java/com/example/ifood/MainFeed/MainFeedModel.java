package com.example.ifood.MainFeed;

public class MainFeedModel {

    String UserName;
    String UserImage;
    String Post;

    public MainFeedModel(String userName, String userImage, String post) {
        UserName = userName;
        UserImage = userImage;
        Post = post;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public String getPost() {
        return Post;
    }
}
