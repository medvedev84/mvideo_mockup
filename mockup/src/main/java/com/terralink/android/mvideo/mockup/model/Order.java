package com.terralink.android.mvideo.mockup.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private String address;
    private OrderStatus status;
    private List<Product> products = new ArrayList<Product>();

    public Order(String address, OrderStatus status) {
        this.address = address;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
