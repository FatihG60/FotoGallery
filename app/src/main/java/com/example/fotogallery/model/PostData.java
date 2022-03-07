package com.example.fotogallery.model;


import java.util.ArrayList;

public class PostData {

    private ArrayList<String> postTitles;
    private ArrayList<String> postImages;
    private ArrayList<String> postUserNames;
    private ArrayList<Number> postLikes;
    private ArrayList<String> postIds;


    public PostData() {
        postTitles = new ArrayList<>();
        postImages = new ArrayList<>();
        postUserNames = new ArrayList<>();
        postLikes = new ArrayList<>();
        postIds = new ArrayList<>();
    }

    public void setPostTitles(ArrayList<String> postTitles) {
        this.postTitles = postTitles;
    }

    public ArrayList<String> getPostImages() {
        return postImages;
    }

    public void setPostImages(ArrayList<String> postImages) {
        this.postImages = postImages;
    }

    public ArrayList<String> getPostTitles() {
        return postTitles;
    }

    public ArrayList<String> getPostUserNames() {
        return postUserNames;
    }

    public void setPostUserNames(ArrayList<String> postUserNames) {
        this.postUserNames = postUserNames;
    }

    public ArrayList<Number> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(ArrayList<Number> postLikes) {
        this.postLikes = postLikes;
    }

    public ArrayList<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(ArrayList<String> postIds) {
        this.postIds = postIds;
    }
}
