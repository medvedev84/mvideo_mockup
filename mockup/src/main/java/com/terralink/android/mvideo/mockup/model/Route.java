package com.terralink.android.mvideo.mockup.model;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private String name;
    private List<Order> orders = new ArrayList<Order>();

    public Route(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
