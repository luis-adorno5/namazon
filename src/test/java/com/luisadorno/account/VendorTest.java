package com.luisadorno.account;

import com.luisadorno.product.Product;
import com.luisadorno.product.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VendorTest {

    private Vendor vendor;
    private Product product;

    @BeforeEach
    void setUp() {
        vendor = new Vendor("Chulito's", "Luis",
                "Adorno", "chulito@vendor.com",
                "12345");
        product = new Product("Playstation",
                ProductCategory.ELECTRONICS, 499.99);

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

}
