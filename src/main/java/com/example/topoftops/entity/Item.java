package com.example.topoftops.entity;

import java.util.Objects;


/**
 * Describes the entity Item
 *
 * @author Ilya Tsvetkov
 */
public class Item {
    private long id;
    private long top;
    private String title;
    private String description;
    private String image;
    private int place;

    /**
     * Constructs a new Item
     */
    public Item() {
    }

    /**
     * Constructs a new Item with params
     *
     * @param title       {@link String} title
     * @param description {@link String} description
     * @param image       {@link String} image
     */
    public Item(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

        if (id != item.id) return false;
        if (top != item.top) return false;
        if (place != item.place) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        return image != null ? image.equals(item.image) : item.image == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (top ^ (top >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + place;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("id=").append(id);
        sb.append(", top=").append(top);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", place=").append(place);
        sb.append('}');
        return sb.toString();
    }
}
