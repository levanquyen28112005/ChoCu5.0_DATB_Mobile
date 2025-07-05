package edu.mb.oldzy.domain.model;

import com.google.gson.annotations.SerializedName;

import edu.mb.oldzy.data.model.UserModel;

public class UserResponse {

    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("role")
    private String role;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("full_name")
    private String fullName;

    @SerializedName("createdDate")
    private String createdDate;

    public UserResponse(String username, String email, String phoneNumber, String role, String avatar, String fullName, String createdDate) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.avatar = avatar;
        this.fullName = fullName;
        this.createdDate = createdDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public UserModel toUserModel() {
        return new UserModel(username, email, phoneNumber, role, avatar, fullName, createdDate);
    }
}
