package com.luisadorno.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void constructorTest01() {
        Product product = new Product("Playstation",
                ProductCategory.ELECTRONICS, 499.99);
        String expected = "Playstation ELECTRONICS 499.99";
        String actual = product.toString();
        Assertions.assertEquals(expected, actual);
    }
}
