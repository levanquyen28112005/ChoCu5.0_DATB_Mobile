package edu.mb.oldzy.data.model;

import android.net.Uri;

import java.io.Serializable;

public class ImagePostModel implements Serializable {
    private Uri imageUri;
    private boolean isAddImage;

    public ImagePostModel(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public ImagePostModel(boolean isAddImage) {
        this.isAddImage = isAddImage;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isAddImage() {
        return isAddImage;
    }

    public void setAddImage(boolean addImage) {
        isAddImage = addImage;
    }
}
