package com.example.topoftops.entity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Describes the entity Top
 *
 * @author Ilya Tsvetkov
 */
public class Top {

    private long id;
    private long user;
    private String title;
    private String description;
    private String image;
    private ArrayList<Item> items;
    private int rating;

    /**
     * Constructs a new Top
     */
    public Top() {
    }


    /**
     * Constructs a new Top with params
     *
     * @param user        {@link Long} user id
     * @param title       {@link String} title
     * @param image       {@link String} image
     * @param items       {@link ArrayList<Item>} items
     * @param description {@link String} description
     */
    public Top(String title, String description, String image, ArrayList<Item> items, long user) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.items = items;
        this.user = user;
    }

    /**
     * Constructs a new Top with params without items
     *
     * @param user        {@link Long} user id
     * @param title       {@link String} title
     * @param image       {@link String} image
     * @param description {@link String} description
     */
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Top top = (Top) o;

        if (id != top.id) return false;
        if (user != top.user) return false;
        if (rating != top.rating) return false;
        if (!title.equals(top.title)) return false;
        if (!description.equals(top.description)) return false;
        if (!image.equals(top.image)) return false;
        return items != null ? items.equals(top.items) : top.items == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (user ^ (user >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + image.hashCode();
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + rating;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Top{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", items=").append(items);
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }
}
