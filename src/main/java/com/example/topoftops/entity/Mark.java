package com.example.topoftops.entity;

public class Mark {
    private long id;
    private long top;
    private long user;
    private int mark;

    public Mark() {
    }

    public Mark(long id, long top, long user, int mark) {
        this.id = id;
        this.top = top;
        this.user = user;
        this.mark = mark;
    }


    public Mark(long top, long user, int mark) {
        this.top = top;
        this.user = user;
        this.mark = mark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }


}
