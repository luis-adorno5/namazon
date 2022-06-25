package com.luisadorno.system;

import com.luisadorno.account.Account;
import com.luisadorno.account.Customer;
import com.luisadorno.account.Vendor;
import com.luisadorno.exceptions.AccountCreationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Namazon {

    private static final Scanner scanner = new Scanner(System.in);
    private List<Customer> customers;
    private List<Vendor> vendors;

    public Namazon(){
        this.customers = new ArrayList<>();
        this.vendors = new ArrayList<>();
    }

    public void start() throws AccountCreationException {
        Account account = null;
        boolean sessionOn = true;
        while (sessionOn){
            int choice = signInSignUpDisplay();
            switch (choice) {
                case 0 -> account = signInAsCustomer();
                case 1 -> account = signInAsVendor();
                case 2 -> account = signUpAsVendor();
                case 3 -> account = signUpAsCustomer();
                case 4 -> sessionOn = false;
                default -> System.out.println("You did not input a valid option.\n");
            }
            if(account instanceof Vendor)
                System.out.println("You are signed in as a vendor");
            else if(account instanceof Customer)
                System.out.println("You signed in as a Customer.");
            else
                System.out.println("You do not have a valid account!");
        }
    }

    public Customer signInAsCustomer(){
        System.out.println("You chose sign in as customer.");
        return null;
    }

    public Vendor signInAsVendor(){
        System.out.println("You chose sign in as vendor.");
        return null;
    }

    public Vendor signUpAsVendor() throws AccountCreationException {
        System.out.println("Enter your First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your Last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your brand name: ");
        String brandName = scanner.nextLine();
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if(isEmailValid(email) && !isEmailRegistered(email))
            return createVendorWithInformation(firstName, lastName, brandName, email, password);
        throw new AccountCreationException();
    }

    private Vendor createVendorWithInformation(String firstName, String lastName, String brandName, String email, String password){
        return new Vendor(brandName, firstName, lastName, email, password);
    }

    public Customer signUpAsCustomer() throws AccountCreationException {
        System.out.println("Enter your First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your Last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if(isEmailValid(email) && !isEmailRegistered(email))
            return new Customer(firstName, lastName, email, password);
        throw new AccountCreationException();
    }

    private Boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        return pattern.matcher(email).matches();
    }

    private Boolean isEmailRegistered(String email){
        return vendors.stream().anyMatch(vendor -> vendor.getEmail().equals(email));
    }

    public Vendor selectVendor(){
        return null;
    }

    private Integer signInSignUpDisplay(){
        System.out.println("Welcome to Namazon!");
        System.out.println("0) Sign in as customer");
        System.out.println("1) Sign in as vendor");
        System.out.println("2) Sign up as vendor");
        System.out.println("3) Sign up as customer");
        System.out.println("4) Exit");
        return Integer.parseInt(scanner.nextLine());

    }

    public static void main(String[] args) throws AccountCreationException {
        Namazon namazon = new Namazon();
        namazon.start();
    }
}
