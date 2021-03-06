package com.luisadorno.system;

import com.luisadorno.account.Account;
import com.luisadorno.account.Customer;
import com.luisadorno.account.Vendor;
import com.luisadorno.address.Address;
import com.luisadorno.exceptions.AccountCreationException;
import com.luisadorno.order.Order;
import com.luisadorno.product.Product;
import com.luisadorno.product.ProductCategory;

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
        Account account;
        boolean sessionOn = true;
        boolean signedIn;
        while (sessionOn){
            account = null;
            signedIn = true;
            int choice = signInSignUpDisplay();
            switch (choice) {
                case 0 -> account = signInAsCustomer();
                case 1 -> account = signInAsVendor();
                case 2 -> account = signUpAsVendor();
                case 3 -> account = signUpAsCustomer();
                case 4 -> sessionOn = false;
                default -> System.out.println("You did not input a valid option.\n");
            }
            if(account instanceof Vendor){
                while (signedIn){
                    switch (vendorMenu()){
                        case 0 -> addProductToInventory((Vendor) account);
                        case 1 -> changeShowcase((Vendor) account);
                        case 2 -> signedIn = false;
                    }
                }
            }
            else if(account instanceof Customer) {
                while (signedIn){
                    Vendor vendor = selectVendor();
                    switch (customerMenu()){
                        case 0 -> purchase(vendor, ((Customer) account));
                        case 1 -> listCustomersOngoingOrders((Customer) account);
                        case 2 -> signedIn = false;
                    }
                }
            }
            else if(choice == 4){
                System.out.println("Goodbye we hope to see you again!");
            }
            else
                System.out.println("You do not have a valid account!");
        }
    }

    private Integer vendorMenu(){
        System.out.println("What would you like to do?");
        System.out.println("0) Add a product to inventory");
        System.out.println("1) Add a product to showcase");
        System.out.println("2) Sign out");
        return Integer.parseInt(scanner.nextLine());
    }

    private Integer customerMenu(){
        System.out.println("What would you like to do?");
        System.out.println("0) Buy a product");
        System.out.println("1) List current orders");
        System.out.println("2) Sign out");
        return Integer.parseInt(scanner.nextLine());
    }

    private Order listCustomersOngoingOrders(Customer customer){
        for(Order order : customer.getOrders()){
            System.out.println(order.toString());
        }
        return null;
    }

    private Product addProductToInventory(Vendor vendor){
        boolean addingProducts = true;
        Product product = new Product("", ProductCategory.ELECTRONICS, 0.00);
        while (addingProducts){
            System.out.println("What type of product would you like to add?");
            System.out.println("0) "+ ProductCategory.ELECTRONICS);
            System.out.println("1) "+ ProductCategory.ATHLETICS);
            System.out.println("2) "+ ProductCategory.CLOTHING);
            System.out.println("3) "+ ProductCategory.HOME_APPLIANCE);
            System.out.println("4) Stop adding products");
            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice){
                case 0 -> product = createProduct(ProductCategory.ELECTRONICS, vendor);
                case 1 -> product = createProduct(ProductCategory.ATHLETICS, vendor);
                case 2 -> product = createProduct(ProductCategory.CLOTHING, vendor);
                case 3 -> product = createProduct(ProductCategory.HOME_APPLIANCE, vendor);
                case 4 -> addingProducts = false;
            }
        }
        return product;
    }

    private Product createProduct(ProductCategory category, Vendor vendor){
        System.out.println("Enter the name of the product: ");
        String name = scanner.nextLine();
        System.out.println("Enter the product price: ");
        double price = Double.parseDouble(scanner.nextLine());
        Product newProduct = new Product(name, category, price);
        vendor.addProductToInventory(newProduct);
        System.out.println("Added product to inventory.\n");
        return newProduct;
    }

    private Product changeShowcase(Vendor vendor){
        if(vendor.getInventory().size() == 0) return null;
        int i = 0;
        System.out.println("Which product would you like to display in the showcase: ");
        for (Product product : vendor.getInventory().keySet()){
            System.out.println(i + ") " + product.toString());
        }
        int choice = Integer.parseInt(scanner.nextLine());
        Product product = (Product) vendor.getInventory().keySet().toArray()[0];
        System.out.println("Which showcase spot from 1-5 would you like to place the product?");
        int showcaseIndex = Integer.parseInt(scanner.nextLine());
        vendor.getShowcase()[showcaseIndex] = product;
        return product;
    }

    private Customer signInAsCustomer(){
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        if(!isCustomerEmailRegistered(email)){
            System.out.println("The email you entered is not registered!");
            return null;
        }
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if(isCorrectPasswordForCustomerAccount(email, password)){
            for(Customer customer : customers){
                if(customer.getEmail().equals(email)) return customer;
            }
        }
        return null;
    }

    private Vendor signInAsVendor(){
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        if(!isVendorEmailRegistered(email)){
            System.out.println("The email you entered is not registered!");
            return null;
        }
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if(isCorrectPasswordForVendorAccount(email, password)){
            for(Vendor vendor : vendors){
                if(vendor.getEmail().equals(email)) return vendor;
            }
        }
        return null;
    }

    private Boolean isCorrectPasswordForVendorAccount(String email, String password){
        for (Vendor vendor : vendors){
            if(vendor.getEmail().equals(email) && vendor.getPassword().equals(password))
                return true;
        }
        return false;
    }

    private Boolean isCorrectPasswordForCustomerAccount(String email, String password){
        for (Customer customer : customers){
            if(customer.getEmail().equals(email) && customer.getPassword().equals(password))
                return true;
        }
        return false;
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
        if(isEmailValid(email) && !isVendorEmailRegistered(email))
            return createVendorWithInformation(firstName, lastName, brandName, email, password);
        throw new AccountCreationException();
    }

    private Vendor createVendorWithInformation(String firstName, String lastName, String brandName, String email, String password){
        Vendor vendor = new Vendor(brandName, firstName, lastName, email, password);
        vendors.add(vendor);
        return vendor;
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
        Address address = enterAddressInformation();
        if(isEmailValid(email) && !isCustomerEmailRegistered(email)) {
            Customer customer = new Customer(firstName, lastName, email, password, address);
            customers.add(customer);
            return customer;
        }
        throw new AccountCreationException();
    }

    private Address enterAddressInformation(){
        System.out.println("Enter your street: ");
        String street = scanner.nextLine();
        System.out.println("Enter your unit: ");
        String unit = scanner.nextLine();
        System.out.println("Enter your city: ");
        String city = scanner.nextLine();
        System.out.println("Enter your state: ");
        String state = scanner.nextLine();
        return new Address(street, unit, city, state);
    }

    private Order purchase(Vendor vendor, Customer customer){
        boolean purchasing = true;
        int i = 0;
        Order order = null;
        while (purchasing){
            System.out.println("The available products are: ");
            for (Product product : vendor.getInventory().keySet()){
                System.out.println(i+") " + product.toString());
                i++;
            }
            System.out.println(i+") Stop purchasing");
            System.out.println("Which product would you like to purchase?");
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice == i) purchasing = false;
            else if(choice < 0 || choice > vendors.size()) System.out.println("You did not select a valid option.");
            else {
                Product product = ((Product) vendor.getInventory().keySet().toArray()[choice]);
                order = vendor.placeAnOrder(product, customer.getAddress());
                customer.getOrders().add(order);
                System.out.println("You have successfully purchased a " + product.toString()+"\n");
            }
        }
        return order;
    }

    private Boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        return pattern.matcher(email).matches();
    }

    private Boolean isVendorEmailRegistered(String email){
        return vendors.stream().anyMatch(vendor -> vendor.getEmail().equals(email));
    }

    private Boolean isCustomerEmailRegistered(String email){
        return customers.stream().anyMatch(customer -> customer.getEmail().equals(email));
    }

    public Vendor selectVendor(){
        int i = 0;
        int choice = -1;
        boolean notChosen = true;
        while(notChosen) {
            System.out.println("Which vendor would you like to choose?");
            for (Vendor vendor : vendors) {
                System.out.println(i + ") " + vendor.getBrandName());
                i++;
            }
            choice = Integer.parseInt(scanner.nextLine());
            if(choice < 0 || choice > vendors.size())
                System.out.println("You did not select a valid option.");
            else
                notChosen = false;
        }
        return vendors.get(choice);
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
