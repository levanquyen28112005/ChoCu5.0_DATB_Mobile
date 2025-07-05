package edu.mb.oldzy.domain.model;

import com.google.gson.annotations.SerializedName;

import edu.mb.oldzy.data.model.CategoryModel;

public class CategoryResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;
    @SerializedName("parent")
    private CategoryResponse parent;

    @SerializedName("description")
    private String description;

    public CategoryResponse(int id, String name, String image, CategoryResponse parent, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.parent = parent;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryResponse getParent() {
        return parent;
    }

    public void setParent(CategoryResponse parent) {
        this.parent = parent;
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

    public CategoryModel toCategoryModel() {
        String parentId = null;
        if (parent != null) {
            parentId = String.valueOf(parent.getId());
        }
        return new CategoryModel(String.valueOf(id), name, description, image, parentId);
    }
}
