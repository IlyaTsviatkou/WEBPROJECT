package com.example.topoftops.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Top {
    private long id;
    private long user;
    private String title;
    private String description;
    private String image;
    private ArrayList<Item> items;

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



    @Override
    public String toString() {
        return "Top{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Top top = (Top) o;
        return Objects.equals(title, top.title) && Objects.equals(description, top.description) && Objects.equals(image, top.image) && Objects.equals(items, top.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, image, items);
    }

    public Top() {
    }

    public Top(String title, String description, String image, ArrayList<Item> items , long user) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.items = items;
        this.user = user;
    }

    public Top(String title, String description, String image, long user) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.user = user;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
