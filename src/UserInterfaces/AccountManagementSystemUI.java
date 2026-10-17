package userinterfaces;

import util.*;
import objects.Account;
import objects.Admin;
import objects.Faculty;

public class AccountManagementSystemUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static AccountFileHandler ACCOUNTFILEHANDLER = AccountFileHandler.getInstance();

    public void addUserAccount(){
        System.out.println("=======================================================");
        System.out.println("                    Add New Account                    ");
        System.out.println("=======================================================");

        Account account = null;
        String username = promptUsername();
        String password = promptPassword();

        System.out.println("--------------------------------------------------------");
        System.out.println("Roles:");
        System.out.println("1. Admin");
        System.out.println("2. Faculty");

        int roleChoice = promptRoleChoice();

        switch (roleChoice){
            case 1:
                account = new Admin(username, password);
                break;
            case 2:
                account = new Faculty(username, password);
                break;
        }
        ACCOUNTFILEHANDLER.saveAccountToFile(account);
    }

    public void viewAllUserAccounts(){

    }

    public void searchUserAccount(){

    }

    public void updateUserAccount(){

    }

    public void deleteUserAccount(){

    }
    
    public String promptUsername(){
        return VALIDATOR.getValidatedStringInput("Enter username: ", "Invalid input! Username cannot be empty.", "^.+$");
    }

    public String promptPassword(){
        return VALIDATOR.getValidatedStringInput("Enter password: ", "Invalid input! Password cannot be empty.", "^.+$");
    }

    public int promptRoleChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Please select a role for the account (1-2): ", "Invalid input! Please enter a number between 1 and 2.", 1, 2);
    }
}
