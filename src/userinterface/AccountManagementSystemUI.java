package userinterface;

import models.Account;
import models.Admin;
import models.Faculty;
import util.*;

public class AccountManagementSystemUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static AccountFileHandler ACCOUNTFILEHANDLER = AccountFileHandler.getInstance();

    public void addUserAccount(){
        System.out.println("=======================================================");
        System.out.println("                    Add New Account                    ");
        System.out.println("=======================================================");
        Account account = null;
        boolean isRunning = true;
        while(isRunning){
            String username = promptNewUsername();
            String password = promptNewPassword();

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
            isRunning = false;
        }
    }

    public void viewAllUserAccounts(){

    }

    public void searchUserAccount(){

    }

    public void updateUserAccount(){

    }

    public void deleteUserAccount(){
        System.out.println("=======================================================");
        System.out.println("                    Delete Account                     ");
        System.out.println("=======================================================");

        String username = promptUsername();

        ACCOUNTFILEHANDLER.deleteAccount(username);

    }

    public String promptUsername(){
        return VALIDATOR.getValidatedStringInput("Enter username: ", "Invalid input! Username cannot be empty.", "^.+$");
    }
    
    public String promptNewUsername(){
        String username = ""; 
        boolean validUsername = false;

        while (!validUsername){
            username = VALIDATOR.getValidatedStringInput("Enter username: ", "Invalid input! Username cannot be empty.", "^.+$");

            if (ACCOUNTFILEHANDLER.usernameExists(username)){
                System.out.println("Duplicate Username! Please enter a new username.");
            }
            else{
                validUsername = true;
            }
        }
        return username;
    }

    public String promptNewPassword(){
        return VALIDATOR.getValidatedStringInput("Enter password: ", "Invalid input! Password cannot be empty.", "^.+$");
    }

    public int promptRoleChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Please select a role for the account (1-2): ", "Invalid input! Please enter a number between 1 and 2.", 1, 2);
    }
}
