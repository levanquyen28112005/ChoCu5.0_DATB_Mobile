package edu.mb.oldzy.domain.request;

import com.google.gson.annotations.SerializedName;

public class CategoryRequest {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("parent_id")
    private Long parentId;

    @SerializedName("image")
    private String image;

    public CategoryRequest(String name, String description, Long parentId, String image) {
        this.name = name;
        this.description = description;
        this.parentId = parentId;
        this.image = image;
    }

    public CategoryRequest(int id, String name, String description, Long parentId, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
