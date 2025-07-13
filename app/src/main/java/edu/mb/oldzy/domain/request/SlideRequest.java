package edu.mb.oldzy.domain.request;

import com.google.gson.annotations.SerializedName;

public class SlideRequest {

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    public SlideRequest(String description, String image) {
        this.description = description;
        this.image = image;
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
