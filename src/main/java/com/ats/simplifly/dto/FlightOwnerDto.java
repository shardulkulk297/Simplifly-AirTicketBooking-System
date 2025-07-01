package com.ats.simplifly.dto;

import com.ats.simplifly.model.enums.VerificationStatus;

import java.util.Objects;

public class FlightOwnerDto {
    private int id;
    private String companyName;
    private String email;
    private String contactPhone;
    private VerificationStatus verificationStatus;
    private String logoLink;
    private UserDto user;

    public FlightOwnerDto() {}

    public FlightOwnerDto(int id, String companyName, String email, String contactPhone,
                          VerificationStatus verificationStatus, String logoLink, UserDto user) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.contactPhone = contactPhone;
        this.verificationStatus = verificationStatus;
        this.logoLink = logoLink;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FlightOwnerDto that)) return false;
        return id == that.id && Objects.equals(companyName, that.companyName) && Objects.equals(email, that.email) && Objects.equals(contactPhone, that.contactPhone) && verificationStatus == that.verificationStatus && Objects.equals(logoLink, that.logoLink) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, email, contactPhone, verificationStatus, logoLink, user);
    }
}
