package com.luisadorno.system;

import com.luisadorno.account.Account;
import com.luisadorno.exceptions.UserCredentialsInvalidException;
import com.luisadorno.exceptions.UserDoesNotExistException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NamazonV2 {

    private Map<String, Account> accounts;
    private Account currentAccount;
    private static final Scanner scanner = new Scanner(System.in);

    public NamazonV2(){
        this.accounts = new HashMap<>();
        this.currentAccount = null;
    }

    public NamazonV2(Map<String, Account> users) {
        this.accounts = users;
        this.currentAccount = null;
    }

    public void run(){
        Boolean flag = false;
        while (flag){
            if(currentAccount == null)
                flag = welcomeScreen();
            else{
                accountOptionsScreen();
            }
        }
    }

    private Boolean accountOptionsScreen(){
        return null;
    }

    private Boolean welcomeScreen(){
        Boolean flag = true;
        String output = """
                Welcome to Namazon. What would you like to do?
                0) Sign in as customer
                1) Sign in as vendor
                2) Sign up as customer
                3) Sign up as vendor
                4) Exit""";
        System.out.println(output);
        String selection = scanner.nextLine();
        switch(selection){
            case "0" -> attemptSignIn();
            case "1" -> attemptSignUpAsCustomer();
            case "2" -> attemptSignUpAsVendor();
            case "3"-> {}
            default -> {
                System.out.println("Goodbye!");
                flag = false;
            }
        }
        return flag;
    }

    private void attemptSignIn(){
        try{
            System.out.println("Please enter a valid email:");
            String email = scanner.nextLine();
            System.out.println("Please enter a valid password:");
            String password = scanner.nextLine();
            currentAccount = signIn(email, password);
        }
        catch(UserCredentialsInvalidException e){
            System.out.println("You entered the wrong password!");
        }
        catch(UserDoesNotExistException e){
            System.out.println("The email you entered is not registered!");
        }
    }

    public Account signIn(String email, String password) throws UserDoesNotExistException, UserCredentialsInvalidException{
        if(!accounts.containsKey(email))
            throw new UserDoesNotExistException();
        Account account = accounts.get(email);
        if(!validatePassword(account, password))
            throw new UserCredentialsInvalidException();
        return account;
    }

    private void attemptSignUpAsCustomer(){}

    private void attemptSignUpAsVendor(){}

    private Boolean validatePassword(Account account, String password){
        return account.getPassword().equals(password);
    }

    public static void main(String[] args) {
    }

}
