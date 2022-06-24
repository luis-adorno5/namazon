package com.luisadorno.product;

public class Product {

    private Long id;
    private Long idCount = 0L;
    private String name;
    private ProductCategory category;
    private Double price;

    public Product(String name, ProductCategory category, Double price) {
        this.id = ++idCount;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String toString(){
        return String.format("%s %s %.2f", name, category, price);
    }

}
