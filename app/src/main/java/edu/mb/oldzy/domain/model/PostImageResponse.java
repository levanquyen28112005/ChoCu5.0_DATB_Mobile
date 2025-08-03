package edu.mb.oldzy.domain.model;

public class PostImageResponse {
    private int id;
    private String imageUrl;
    private Integer orderIndex;
    private String createdAt;

    public PostImageResponse() {
    }

    public PostImageResponse(int id, String imageUrl, Integer orderIndex, String createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.orderIndex = orderIndex;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
