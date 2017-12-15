package com.example.android.bookstore.Model;

/**
 * Created by hblgdrl on 16.12.2017.
 */

public class Owner {
    private String ID;
    private String Name;
    private String Surname;
    private String Password;
    private String Username;
    private String Address;
    private String PhoneNumber;


    public Owner(String name, String surname, String password, String address, String phoneNumber) {
        Name = name;
        Surname = surname;
        Password = password;
        Address = address;
        PhoneNumber = phoneNumber;
    }

    public Owner() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
