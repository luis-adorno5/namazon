package com.luisadorno.account;

import com.luisadorno.address.Address;
import com.luisadorno.order.Order;
import com.luisadorno.order.OrderStatus;
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

    public Boolean addProductToInventory(Product product){
        if(product == null) return false;
        if(isInInventory(product)) {
            inventory.put(product, inventory.get(product)+1);
            return true;
        }
        inventory.put(product, 1);
        return true;
    }

    public Boolean removeProductFromInventory(Product product){
        if(isInInventory(product)) {
            inventory.remove(product);
            return true;
        }
        return false;
    }

    private Boolean isInInventory(Product product){
        return inventory.containsKey(product);
    }

    public Order placeAnOrder(Product product, Address destination){
        if(!isInInventory(product)) throw new NoSuchElementException();
        Order order = new Order(product, destination, OrderStatus.PENDING);
        removeProductFromInventory(product);
        orders.add(order);
        return order;
    }

    public Boolean cancelOrder(Order order){
        if(order == null) return false;
        if(checkIfOrderExistsAndHasShipped(order))
            return orders.remove(order);
        return false;
    }

    private Boolean checkIfOrderExistsAndHasShipped(Order order){
        return orders.stream().anyMatch(placedOrder -> placedOrder.getId()
                .equals(order.getId()) &&
                placedOrder.getStatus() != OrderStatus.SHIPPED);
    }

    public Product[] getShowcase() {
        return showcase;
    }

    public void addProductToShowcase(Product product, Integer position){
        if(position < 0 || position > 4) throw new IndexOutOfBoundsException();
        showcase[position] = product;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendor vendor = (Vendor) o;
        return Objects.equals(brandName, vendor.brandName) && Objects.equals(inventory, vendor.inventory) && Arrays.equals(showcase, vendor.showcase) && Objects.equals(orders, vendor.orders);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(brandName, inventory, orders);
        result = 31 * result + Arrays.hashCode(showcase);
        return result;
    }

    @Override
    public String toString() {
        return brandName;
    }
}
