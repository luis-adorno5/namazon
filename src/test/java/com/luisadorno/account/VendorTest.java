package com.luisadorno.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VendorTest {

    @Test
    @DisplayName("Parameterized constructor test.")
    public void constructorTest01() {
        Vendor vendor = new Vendor("Chulito's", "Luis",
                "Adorno", "chulito@vendor.com",
                "12345");
        String expected = "Chulito's";
        String actual = vendor.toString();
        Assertions.assertEquals(expected, actual);
        Assertions.assertNotNull(vendor.getInventory());
    }
}
