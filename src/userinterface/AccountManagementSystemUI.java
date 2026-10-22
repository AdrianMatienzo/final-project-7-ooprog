package userinterface;

import java.util.*;

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
            ACCOUNTFILEHANDLER.addAccount(account);
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
        List<String> accounts = new ArrayList<>();

        ACCOUNTFILEHANDLER.createAccountList(accounts);

        System.out.println("=======================================================");
        System.out.println("                   Delete Account                      ");
        System.out.println("=======================================================");

        String username = promptUsername();

        if(username.equals("admin") || username.equals("faculty")){
            System.out.println("Cannot delete original accounts.");
            return;
        }

        int accountToDelete = findAccountIndex(accounts, username);

        if (accountToDelete == -1) {
            System.out.println("Username not found!");
            return;
        }

        String[] data = accounts.get(accountToDelete).split("\\|", -1);

        System.out.println("-------------------------------------------------------");
        System.out.println("Account Found");
        System.out.println("Username: " + data[0]);
        System.out.println("Role: " + data[2]);
        System.out.println("-------------------------------------------------------");

        String confirmation = promptConfirmation();

        if (confirmation.equalsIgnoreCase("N")) {
            System.out.println("Account deletion cancelled!");
            return;
        }

        accounts.remove(accountToDelete);

        ACCOUNTFILEHANDLER.saveUpdatedAccounts(accounts);

        System.out.println("Account deleted successfully!");
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

    public String promptConfirmation(){
        return VALIDATOR.getValidatedStringInput("Are you sure you want to delete this student record? (Y/N): ","Invalid input! Please enter Y or N.","^[YyNn]$");
    }

    public int findAccountIndex(List<String> accounts, String username) {

    for (int i = 0; i < accounts.size(); i++) {
        String[] data = accounts.get(i).split("\\|", -1);

        if (data[0].trim().equalsIgnoreCase(username)) {
            return i;
        }
    }
        return -1;
    }
}
