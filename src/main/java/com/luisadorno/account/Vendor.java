package com.luisadorno.account;

import com.luisadorno.order.Order;
import com.luisadorno.product.Product;

import java.util.*;

public class Vendor extends Account{

    private String brandName;
    private Map<Product, Integer> inventory;
    private Product[] showcase;
    private List<Order> orders;

    public Vendor(String brandName, String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.brandName = brandName;
        this.inventory = new HashMap<>();
        this.showcase = new Product[5];
        this.orders = new ArrayList<>();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Map<Product, Integer> getInventory() {
        return inventory;
    }

    public void addProductToInventory(Product product){
    }

    public Product[] getShowcase() {
        return showcase;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return brandName;
    }
}
