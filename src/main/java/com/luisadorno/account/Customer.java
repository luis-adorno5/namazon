package com.luisadorno.account;

import com.luisadorno.address.Address;
import com.luisadorno.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(money, customer.money) && Objects.equals(orders, customer.orders) && Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, orders, address);
    }
}
