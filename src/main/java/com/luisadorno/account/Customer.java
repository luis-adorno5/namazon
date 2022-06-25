package com.luisadorno.account;

import com.luisadorno.address.Address;
import com.luisadorno.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Account{

    private Double money;
    private List<Order> orders;
    private Address address;

    public Customer(String firstName, String lastName, String email, String password, Address address) {
        super(firstName, lastName, email, password);
        this.orders = new ArrayList<>();
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
