package com.stex.core.api.medic.models;

public class Information {

    private String street;

    private String number;

    private String city;

    private String phone;

    public Information() {
    }

    public Information(String street, String number, String city, String phone) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
