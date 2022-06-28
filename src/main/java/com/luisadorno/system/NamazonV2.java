package com.luisadorno.system;

import com.luisadorno.account.Account;
import com.luisadorno.account.Customer;
import com.luisadorno.account.Vendor;
import com.luisadorno.address.Address;
import com.luisadorno.exceptions.InvalidEmailException;
import com.luisadorno.exceptions.UserCredentialsInvalidException;
import com.luisadorno.exceptions.UserDoesNotExistException;
import com.luisadorno.exceptions.UserExistsException;
import com.luisadorno.order.Order;
import com.luisadorno.product.Product;
import com.luisadorno.product.ProductCategory;

import java.util.*;
import java.util.regex.Pattern;

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
        Boolean flag = true;
        while (flag){
            if(currentAccount == null)
                flag = welcomeScreen();
            else{
                try {
                    identifyAccountRole();
                }catch (Exception e){
                    System.out.println("Invalid account.");
                }
            }
        }
    }

    private Boolean welcomeScreen(){
        Boolean flag = true;
        String output = """
                Welcome to Namazon. What would you like to do?
                0) Sign in
                1) Sign up as customer
                2) Sign up as vendor
                3) Exit""";
        System.out.println(output);
        String selection = scanner.nextLine();
        switch(selection){
            case "0" -> attemptSignIn();
            case "1" -> attemptSignUpAsCustomer();
            case "2" -> attemptSignUpAsVendor();
            case "3" -> flag = false;
            default -> {
                System.out.println("You did not select a valid option!");
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

    public Account signUp(String firstName, String lastName, String email, String password, Address address) throws UserExistsException, InvalidEmailException{
        if(!isValidEmail(email))
            throw new InvalidEmailException();
        if(accounts.containsKey(email))
            throw new UserExistsException();
        Account account = new Customer(firstName, lastName, email,
                password, address);
        accounts.put(email, account);
        return account;
    }

    public Account signUp(String brandName, String firstName, String lastName, String email, String password) throws UserExistsException, InvalidEmailException{
        if(!isValidEmail(email))
            throw new InvalidEmailException();
        if(accounts.containsKey(email))
            throw new UserExistsException();
        Account account = new Vendor(brandName, firstName, lastName, email,
                password);
        accounts.put(email, account);
        return account;
    }

    private void attemptSignUpAsCustomer(){
        try {
            System.out.println("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter your last name: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter a valid email:");
            String email = scanner.nextLine();
            System.out.println("Enter a valid password:");
            String password = scanner.nextLine();
            Address address = collectAddressInformation();
            currentAccount = signUp(firstName, lastName, email, password, address);
        }catch (UserExistsException e){
            System.out.println("A user with the provided email already exists.");
        }catch (InvalidEmailException e){
            System.out.println("The email you entered is not valid!");
        }
    }

    private void attemptSignUpAsVendor(){
        try {
            System.out.println("Enter your brand name: ");
            String brandName = scanner.nextLine();
            System.out.println("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter your last name: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter a valid email:");
            String email = scanner.nextLine();
            System.out.println("Enter a valid password:");
            String password = scanner.nextLine();
            currentAccount = signUp(brandName, firstName, lastName, email, password);
        }catch (InvalidEmailException e){
            System.out.println("The email you entered is not valid!");
        }catch (UserExistsException e){
            System.out.println("A user with the provided email already exists.");
        }
    }

    private Address collectAddressInformation(){
        System.out.println("Enter your street:");
        String street = scanner.nextLine();
        System.out.println("Enter your unit:");
        String unit = scanner.nextLine();
        System.out.println("Enter your city:");
        String city = scanner.nextLine();
        System.out.println("Enter your state:");
        String state = scanner.nextLine();
        return new Address(street, unit, city, state);
    }

    private Boolean identifyAccountRole() throws Exception {
        if(currentAccount.getClass() == Customer.class)
            return customerOptionsScreen();
        else if(currentAccount.getClass() == Vendor.class)
            return vendorOptionsScreen();
        throw new Exception();

    }

    private Boolean customerOptionsScreen(){
        String msg = String.format("Welcome %s, what would you like to do?", currentAccount.getFirstName());
        msg += "\n0) Purchase a product"
            + "\n1) View orders"
            + "\n2) Log out";
        System.out.println(msg);
        String selection = scanner.nextLine();
        switch(selection){
            case "0" -> {
                purchaseVendorProduct();
                return false;
            }
            case "1" -> {
                displayCustomerOrders();
                return false;
            }
            case "2" -> {
                currentAccount = null;
                return true;
            }
        }
        return null;
    }

    private void purchaseVendorProduct(){
        String msg = "Select and option: "
                +"\n0) List all vendors"
                +"\n1) Select vendor by category"
                +"\n2) Select vendor by product name"
                +"\n3) Exit";
        System.out.println(msg);
        String selection = scanner.nextLine();
        Vendor vendor = null;
        switch(selection){
            case "0" -> vendor = selectVendorToPurchaseFrom();
            case "1" -> vendor = listVendorWithProductsOfCategory();
            case "2" -> vendor = listVendorWithProduct();
            default -> System.out.println("You did not purchase a product.");
        }
    }

    private Vendor selectVendorToPurchaseFrom(){
        int i = 0;
        List<Vendor> vendors = getVendors();
        if(vendors.isEmpty()) return null;
        for(Vendor vendor : vendors){
            System.out.println(i + ") " + vendor.getBrandName());
        }
        int choice = Integer.parseInt(scanner.nextLine());
        if(choice < 0 || choice > vendors.size()-1) return null;
        return getSelectedVendor(vendors, choice);
    }

    public List<Vendor> getVendors(){
        List<Vendor> vendors = new ArrayList<>();
        for (String email : accounts.keySet()){
            if(accounts.get(email).getClass() == Vendor.class)
                vendors.add(((Vendor) accounts.get(email)));
        }
        return vendors;
    }

    public Vendor getSelectedVendor(List<Vendor> vendors, int index){
        return vendors.get(index);
    }

    private Vendor listVendorWithProductsOfCategory(){
        return null;
    }

    private Vendor listVendorWithProduct(){
        return null;
    }

    private void displayCustomerOrders(){
        Customer customer = ((Customer) currentAccount);
        System.out.println("These are your current orders: ");
        for(Order order : customer.getOrders()){
            System.out.println(order.toString());
        }
        System.out.println();
    }

    private Boolean vendorOptionsScreen(){
        String msg = String.format("Welcome %s, what would you like to do?", currentAccount.getFirstName());
        msg += "\n0) Add a product to inventory"
                + "\n1) Add a product to showcase"
                + "\n2) View orders"
                + "\n3) Log out";
        System.out.println(msg);
        String selection = scanner.nextLine();
        switch(selection){
            case "0" -> displayInventoryCreation();
            case "1" -> {return true;}
            case "2" -> displayOrders();
            default -> {currentAccount = null; return false;}
        }
        return false;
    }

    private void displayInventoryCreation(){
        int i = 0;
        System.out.println("Which category does the product belong to: ");
        for(ProductCategory category : ProductCategory.values()){
            System.out.println(i + ") " + category);
            i++;
        }
        int choice = Integer.parseInt(scanner.nextLine());
        enterProductInformation(ProductCategory.values()[choice]);
    }

    private void enterProductInformation(ProductCategory category){
        System.out.println("Enter the product name: ");
        String productName = scanner.nextLine();
        System.out.println("Enter product price: ");
        double productPrice = Double.parseDouble(scanner.nextLine());
        createProduct(productName, category, productPrice);
    }

    public Product createProduct(String name, ProductCategory category, Double price){
        Product product = new Product(name, category, price);
        ((Vendor) currentAccount).addProductToInventory(product);
        return product;
    }

    private void displayOrders(){
        System.out.println("Your outgoing orders are: ");
        for(Order order : fetchVendorOrders()){
            System.out.println(order.toString());
        }
        System.out.println();
    }

    public List<Order> fetchVendorOrders(){
        return ((Vendor) currentAccount).getOrders();
    }

    private Boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        return pattern.matcher(email).matches();
    }

    private Boolean validatePassword(Account account, String password){
        return account.getPassword().equals(password);
    }

    public static void main(String[] args) {
        NamazonV2 namazonV2 = new NamazonV2();
        namazonV2.run();
    }

}
