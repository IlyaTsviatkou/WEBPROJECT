package com.example.topoftops.entity;


import java.util.ArrayList;

/**
 * Describes the entity report
 *
 * @author Ilya Tsvetkov
 */
public class Report {
    private long id;
    private long top;
    private long user;
    private String description;

    /**
     * Constructs a new Report
     */
    public Report() {
    }

    /**
     * Constructs a new Report with params
     *
     * @param user        {@link Long} user id
     * @param top         {@link Long} top
     * @param description {@link String} description
     */
    public Report(long top, long user, String description) {
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
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (id != report.id) return false;
        if (top != report.top) return false;
        if (user != report.user) return false;
        return description != null ? description.equals(report.description) : report.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (top ^ (top >>> 32));
        result = 31 * result + (int) (user ^ (user >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Report{");
        sb.append("id=").append(id);
        sb.append(", top=").append(top);
        sb.append(", user=").append(user);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
