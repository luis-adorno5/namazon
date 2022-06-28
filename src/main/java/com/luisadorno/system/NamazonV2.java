package com.luisadorno.system;

import com.luisadorno.account.Account;
import com.luisadorno.account.Customer;
import com.luisadorno.address.Address;
import com.luisadorno.exceptions.InvalidEmailException;
import com.luisadorno.exceptions.UserCredentialsInvalidException;
import com.luisadorno.exceptions.UserDoesNotExistException;
import com.luisadorno.exceptions.UserExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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

    private void attemptSignUpAsVendor(){}

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
