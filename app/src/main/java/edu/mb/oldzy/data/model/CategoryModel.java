package edu.mb.oldzy.data.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id;
    private String name;
    private String description;
    private String image;
    private String parentId;

    public CategoryModel(String id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        parentId = null;
    }

    public CategoryModel(String id, String name, String description, String image, String parentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.parentId = parentId;
    }

    public CategoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
