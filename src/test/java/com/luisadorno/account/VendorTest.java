package com.luisadorno.account;

import com.luisadorno.address.Address;
import com.luisadorno.order.Order;
import com.luisadorno.order.OrderStatus;
import com.luisadorno.product.Product;
import com.luisadorno.product.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VendorTest {

    private Vendor vendor;
    private Product product;
    private Address address;

    @BeforeEach
    void setUp() {
        vendor = new Vendor("Chulito's", "Luis",
                "Adorno", "chulito@vendor.com",
                "12345");
        product = new Product("Playstation",
                ProductCategory.ELECTRONICS, 499.99);
        address = new Address();
    }

    @Test
    @DisplayName("Parameterized constructor test.")
    public void constructorTest01() {
        String expected = "Chulito's";
        String actual = vendor.toString();
        Assertions.assertEquals(expected, actual);
        Assertions.assertNotNull(vendor.getInventory());
    }

    @Test
    @DisplayName("Successful add.")
    public void addProductToInventoryTest01() {
        Boolean actual = vendor.addProductToInventory(product);
        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("Should return false when adding null.")
    public void addProductToInventoryTest02() {
        Boolean actual = vendor.addProductToInventory(null);
        Assertions.assertFalse(actual);
    }

    @Test
    @DisplayName("Should remove and return true if product is found.")
    public void removeProductFromInventoryTest01() {
        vendor.addProductToInventory(product);
        Boolean actual = vendor.removeProductFromInventory(product);
        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("Should return false if product is not found.")
    public void removeProductFromInventoryTest02() {
        Boolean actual = vendor.removeProductFromInventory(product);
        Assertions.assertFalse(actual);
    }

    @Test
    @DisplayName("Have a customer purchase a product.")
    public void placeAnOrderMethodTest01() {
        vendor.addProductToInventory(product);
        vendor.placeAnOrder(product, address);
        int expected = 1;
        int actual = vendor.getOrders().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Cancel an order that has not shipped.")
    public void cancelOrder01(){
        vendor.addProductToInventory(product);
        Order order = vendor.placeAnOrder(product, address);
        Boolean actual = vendor.cancelOrder(order);
        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("Add product to specified position in showcase.")
    public void addProductToShowcaseTest(){
        vendor.addProductToShowcase(product, 0);
        String expected = "Playstation ELECTRONICS 499.99";
        String actual = vendor.getShowcase()[0].toString();
        Assertions.assertEquals(expected, actual);
    }

}
