package edu.mb.oldzy.domain.model;

import edu.mb.oldzy.data.model.SlideModel;

public class SlideResponse {
    private int id;
    private String description;
    private String image;

    public SlideResponse(int id, String description, String image) {
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

    public SlideModel toSlideModel() {
        return new SlideModel(id, description, image);
    }
}
