package com.example.bbb;

public class Message {
    private final int id;
    private final String content;
    private final String thread;
    private final String imagePath;
    private final int likes;
    private final int userId;
    private final String userName;

    public Message(int id, String content, String thread, String imagePath, int likes, int userId, String userName) {
        this.id = id;
        this.content = content;
        this.thread = thread;
        this.imagePath = imagePath;
        this.likes = likes;
        this.userId = userId;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getThread() {
        return thread;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getLikes() {
        return likes;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

}
