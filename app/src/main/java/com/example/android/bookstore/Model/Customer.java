package com.example.android.bookstore.Model;

/**
 * Created by hblgdrl on 16.12.2017.
 */

public class Customer {

    //private String ID;
    private String name;
    private String surname;
    private String password;
    private String username;
    private String address;
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String name, String surname, String password, String username, String address, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
