package com.luisadorno.account;

import com.luisadorno.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Account{

    private Double money;
    private List<Order> orders;

    public Customer(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
