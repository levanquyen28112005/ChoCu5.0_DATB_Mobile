package edu.mb.oldzy.domain.request;

import java.util.List;

public class PostRequest {
    private Integer categoryId;
    private boolean isBrandNew;
    private String brand;
    private String color;
    private String other;
    private String price;
    private String title;
    private String description;
    private String sellerPhone;
    private String sellerAddress;
    private String createBy;
    private List<String> imageUrls;

    public PostRequest() {
    }

    public PostRequest(Integer categoryId, boolean isBrandNew, String brand, String color, String other,
                       String price, String title, String description, String sellerPhone,
                       String sellerAddress, String createBy, List<String> imageUrls) {
        this.categoryId = categoryId;
        this.isBrandNew = isBrandNew;
        this.brand = brand;
        this.color = color;
        this.other = other;
        this.price = price;
        this.title = title;
        this.description = description;
        this.sellerPhone = sellerPhone;
        this.sellerAddress = sellerAddress;
        this.createBy = createBy;
        this.imageUrls = imageUrls;
    }

    public PostRequest(Integer categoryId, boolean isBrandNew, String brand, String color, String other,
                       String price, String title, String description, String sellerPhone,
                       String sellerAddress, String createBy) {
        this.categoryId = categoryId;
        this.isBrandNew = isBrandNew;
        this.brand = brand;
        this.color = color;
        this.other = other;
        this.price = price;
        this.title = title;
        this.description = description;
        this.sellerPhone = sellerPhone;
        this.sellerAddress = sellerAddress;
        this.createBy = createBy;
        this.imageUrls = List.of();
    }

    // Getters & Setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isBrandNew() {
        return isBrandNew;
    }

    public void setBrandNew(boolean brandNew) {
        isBrandNew = brandNew;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public PostRequest copyWithImageUrls(List<String> imageUrls) {
        PostRequest copy = new PostRequest();

        copy.setCategoryId(this.getCategoryId());
        copy.setBrandNew(this.isBrandNew());
        copy.setBrand(this.getBrand());
        copy.setColor(this.getColor());
        copy.setOther(this.getOther());
        copy.setPrice(this.getPrice());
        copy.setTitle(this.getTitle());
        copy.setDescription(this.getDescription());
        copy.setSellerPhone(this.getSellerPhone());
        copy.setSellerAddress(this.getSellerAddress());
        copy.setCreateBy(this.getCreateBy());
        copy.setImageUrls(imageUrls);

        return copy;
    }
}
