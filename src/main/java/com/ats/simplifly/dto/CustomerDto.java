package com.ats.simplifly.dto;

import java.util.Objects;

public class CustomerDto {
    private int id;
    private String fullName;
    private String email;
    private String contactNumber;
    private String address;
    private String imageLink;
    private UserDto user;

    public CustomerDto() {
    }

    public CustomerDto(int id, String fullName, String email, String contactNumber, String address, String imageLink, UserDto user) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.address = address;
        this.imageLink = imageLink;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomerDto that)) return false;
        return id == that.id && Objects.equals(fullName, that.fullName) && Objects.equals(email, that.email) && Objects.equals(contactNumber, that.contactNumber) && Objects.equals(address, that.address) && Objects.equals(imageLink, that.imageLink) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, contactNumber, address, imageLink, user);
    }
}
