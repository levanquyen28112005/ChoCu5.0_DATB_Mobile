package edu.mb.oldzy.data.model;

public class SlideModel {
    private int id;
    private String description;
    private String image;

    public SlideModel(int id, String description, String image) {
        this.id = id;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
