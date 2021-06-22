package com.example.topoftops.entity;

public class Comment {
    private long id;
    private long user;
    private long top;
    private String message;

    public Comment() {
    }

    public Comment(long user, long top, String message) {
        this.user = user;
        this.top = top;
        this.message = message;
    }

    public Comment(long id, long user, long top, String message) {
        this.id = id;
        this.user = user;
        this.top = top;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getTop() {
        return top;
    }

    public void setTop(long top) {
        this.top = top;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
