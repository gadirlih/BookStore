package com.example.android.bookstore.Model;

import java.util.List;

/**
 * Created by hblgdrl on 17.12.2017.
 */

public class Request {

    private String PhoneNumber;
    private String Name;
    private String Address;
    private String Total;
    private List<Order> Books;  //List of food order

    public Request() {
    }

    public Request(String phoneNumber, String name, String address, String total, List<Order> books) {
        PhoneNumber = phoneNumber;
        Name = name;
        Address = address;
        Total = total;
        Books = books;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public List<Order> getBooks() {
        return Books;
    }

    public void setBooks(List<Order> books) {
        Books = books;
    }
}
