package com.luisadorno.order;

import com.luisadorno.product.Product;

import com.luisadorno.address.Address;

public class Order {

    private Long id;
    private Long idCount = 0L;
    private Product product;
    private Address destination;
    private OrderStatus status;

    public Order(Product product, Address destination, OrderStatus status) {
        this.id = ++idCount;
        this.product = product;
        this.destination = destination;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
