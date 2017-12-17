package com.example.android.bookstore.Model;

/**
 * Created by hblgdrl on 16.12.2017.
 */

public class Book {
    private String Image;
    private String Title, Author, Category, Year, Price, Bookstore;

    public Book() {
    }


    public Book(String image, String title, String author, String category, String year, String price, String bookstore) {
        Image = image;
        Title = title;
        Author = author;
        Category = category;
        Year = year;
        Price = price;
        Bookstore = bookstore;
    }

    public String getBookstore() {
        return Bookstore;
    }

    public void setBookstore(String bookstore) {
        Bookstore = bookstore;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
