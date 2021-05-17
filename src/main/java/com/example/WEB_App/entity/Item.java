package com.example.WEB_App.entity;

import java.util.Objects;

public class Item {
    private long id;
    private long top;
    private String title;
    private String description;
    private String image;



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


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Item() {
    }

    public Item(String title, String description , String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", Description='" + description + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        Item item = (Item) o;
        return Objects.equals(title, item.title) && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
