package edu.mb.oldzy.domain.model;

import java.util.List;

public class PostResponse {

    private int id;
    private CategoryResponse category;
    private String brand;
    private String color;
    private String other;
    private String price;
    private String title;
    private String description;
    private String sellerPhone;
    private String sellerAddress;
    private String createBy;
    private List<PostImageResponse> images;
    private String createdAt;
    private String updatedAt;
    private String status;
    private boolean brandNew;

    public PostResponse() {
    }

    public PostResponse(int id, CategoryResponse category, String brand, String color, String other, String price, String title, String description, String sellerPhone, String sellerAddress, String createBy, List<PostImageResponse> images, String createdAt, String updatedAt, String status, boolean brandNew) {
        this.id = id;
        this.category = category;
        this.brand = brand;
        this.color = color;
        this.other = other;
        this.price = price;
        this.title = title;
        this.description = description;
        this.sellerPhone = sellerPhone;
        this.sellerAddress = sellerAddress;
        this.createBy = createBy;
        this.images = images;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.brandNew = brandNew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public List<PostImageResponse> getImages() {
        return images;
    }

    public void setImages(List<PostImageResponse> images) {
        this.images = images;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isBrandNew() {
        return brandNew;
    }

    public void setBrandNew(boolean brandNew) {
        this.brandNew = brandNew;
    }
}
