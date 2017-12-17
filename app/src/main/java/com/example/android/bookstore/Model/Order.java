package com.example.android.bookstore.Model;

/**
 * Created by hblgdrl on 17.12.2017.
 */

public class Order {

    private String ProductId, ProductName, Quantity, Price, Bookstore;

    public Order(String productId, String productName, String quantity, String price, String bookstore) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Bookstore = bookstore;
    }

    public Order() {
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getBookstore() {
        return Bookstore;
    }

    public void setBookstore(String bookstore) {
        Bookstore = bookstore;
    }
}
