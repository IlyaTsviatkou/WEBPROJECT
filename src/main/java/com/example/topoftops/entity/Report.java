package com.example.topoftops.entity;

public class Report {
    private long id ;
    private long top;
    private long user;
    private String description;

    public Report() {
    }

    public Report(long top, long user, String description) {
        this.top = top;
        this.user = user;
        this.description = description;
    }

    public Report(long id, long top, long user, String description) {
        this.id = id;
        this.top = top;
        this.user = user;
        this.description = description;
    }

    public long getTop() {
        return top;
    }

    public void setTop(long top) {
        this.top = top;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }
}
