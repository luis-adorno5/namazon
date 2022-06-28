package com.luisadorno.namazon;

import com.luisadorno.account.Account;
import com.luisadorno.account.Customer;
import com.luisadorno.address.Address;
import com.luisadorno.exceptions.InvalidEmailException;
import com.luisadorno.exceptions.UserCredentialsInvalidException;
import com.luisadorno.exceptions.UserDoesNotExistException;
import com.luisadorno.exceptions.UserExistsException;
import com.luisadorno.system.Namazon;
import com.luisadorno.system.NamazonV2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class NamazonV2Test {
    private Map<String, Account> accounts;
    private String email;
    private String password;
    private Account account;
    private Address address;

    @BeforeEach
    void setUp() {
        accounts = new HashMap<>();
        email = "fake@fakemail.com";
        password = "1234";
        address = new Address("DF", "SD2", "DFG", "RE");
        account = new Customer("Luis", "Adorno",
                email, password, address);
        accounts.put(email, account);
    }


    @Test
    @DisplayName("Sign in test - successful")
    public void signInTest01() throws UserCredentialsInvalidException, UserDoesNotExistException {
        NamazonV2 namazonV2 = new NamazonV2(accounts);
        Account actualAccount = namazonV2.signIn(email, password);
        Assertions.assertEquals(account, actualAccount);
    }

    @Test
    @DisplayName("Sign in test fail - user not found.")
    public void signInTest02(){
        Assertions.assertThrows(UserDoesNotExistException.class, () ->{
            NamazonV2 namazonV2 = new NamazonV2();
            Account actualAccount = namazonV2.signIn(email, password);
        });
    }

    @Test
    @DisplayName("Sign in test fail - incorrect credentials.")
    public void signInTest03() {
        Assertions.assertThrows(UserCredentialsInvalidException.class, () -> {
            NamazonV2 namazonV2 = new NamazonV2(accounts);
            Account account = namazonV2.signIn(email, "fds");
        });
    }

    @Test
    @DisplayName("Sign up test - Successful account creation")
    public void signUpTest01() throws UserExistsException, InvalidEmailException {
        NamazonV2 namazonV2 = new NamazonV2();
        String expected = email;
        String actual = namazonV2.signUp("Luis", "Adorno",
                email, password, address).getEmail();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sign up test - Fails, InvalidEmailException")
    public void signUpTest02(){
        Assertions.assertThrows(InvalidEmailException.class, () -> {
            NamazonV2 namazonV2 = new NamazonV2();
            namazonV2.signUp("Luis", "Adorno", "as", password, address);
        });
    }

    @Test
    @DisplayName("Sign up test - Fails, UserExistsException")
    public void signUpTest03(){
        Assertions.assertThrows(UserExistsException.class, () -> {
            NamazonV2 namazonV2 = new NamazonV2(accounts);
            namazonV2.signUp("Luis", "Adorno", email, password, address);
        });
    }

}
