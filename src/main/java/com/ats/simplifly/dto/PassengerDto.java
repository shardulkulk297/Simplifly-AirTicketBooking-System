package com.ats.simplifly.dto;

import com.ats.simplifly.model.enums.Gender;

public class PassengerDto {
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private String passportNumber;
    private String nationality;
    private CustomerDto customer;

    public PassengerDto() {}

    public PassengerDto(int id, String name, int age, Gender gender,
                        String passportNumber, String nationality, CustomerDto customer) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}
