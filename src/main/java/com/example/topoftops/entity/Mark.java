package com.example.topoftops.entity;


/**
 * Describes the entity Mark
 *
 * @author Ilya Tsvetkov
 */
public class Mark {
    private long id;
    private long top;
    private long user;
    private int mark;

    /**
     * Constructs a new Mark
     */
    public Mark() {
    }

    /**
     * Constructs a new Report with params
     *
     * @param user {@link Long} user id
     * @param top  {@link Long} user top
     * @param mark {@link Integer} mark
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mark mark1 = (Mark) o;

        if (id != mark1.id) return false;
        if (top != mark1.top) return false;
        if (user != mark1.user) return false;
        return mark == mark1.mark;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (top ^ (top >>> 32));
        result = 31 * result + (int) (user ^ (user >>> 32));
        result = 31 * result + mark;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mark{");
        sb.append("id=").append(id);
        sb.append(", top=").append(top);
        sb.append(", user=").append(user);
        sb.append(", mark=").append(mark);
        sb.append('}');
        return sb.toString();
    }
}
