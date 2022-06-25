package com.luisadorno.namazon;

import com.luisadorno.account.Customer;
import com.luisadorno.account.Vendor;
import com.luisadorno.system.Namazon;
import org.junit.jupiter.api.BeforeEach;

public class NamazonTest {

    private Namazon namazon;
    private Vendor vendor;
    private Customer customer;

    @BeforeEach
    void setUp() {
        namazon = new Namazon();
        vendor = new Vendor("Chulito's",
                "Luis", "Adorno",
                "fakemail@mail.com", "12345");
    }
}
