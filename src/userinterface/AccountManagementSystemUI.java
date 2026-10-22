package userinterface;

import java.util.List;
import java.util.ArrayList;

import models.Account;
import models.AccountIndex;
import models.Admin;
import models.Faculty;

import util.AccountFileHandler;
import util.InputValidation;

public class AccountManagementSystemUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static AccountFileHandler ACCOUNTFILEHANDLER = AccountFileHandler.getInstance();

    public void addUserAccount(){
        System.out.println("=======================================================");
        System.out.println("                    Add New Account                    ");
        System.out.println("=======================================================");
        Account account = null;
        String username = promptNewUsername();
        String password = promptPassword();

        System.out.println("--------------------------------------------------------");
        System.out.println("Roles:");
        System.out.println("1. Admin");
        System.out.println("2. Faculty");
        System.out.println("--------------------------------------------------------");

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

        System.out.println("Account added successfully!");
    }

    public void viewAllUserAccounts(){
        List<String> accounts = new ArrayList<>();

        ACCOUNTFILEHANDLER.createAccountList(accounts);

        System.out.println("===============================================================");
        System.out.println("                     All User Accounts                         ");
        System.out.println("===============================================================");

        System.out.printf("%-25s %-20s %-15s%n", "Username", "Password", "Role");

        System.out.println("---------------------------------------------------------------");

        for (String account : accounts) {

            String[] data = account.split("\\|", -1);

            System.out.printf("%-25s %-20s %-15s%n", data[AccountIndex.USERNAME.getIndex()], data[AccountIndex.PASSWORD.getIndex()], data[AccountIndex.ROLE.getIndex()]);
        }

        System.out.println("===============================================================");
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("===============================================================");
    }

    public void searchUserAccount(){
        List<String> accounts = new ArrayList<>();

        ACCOUNTFILEHANDLER.createAccountList(accounts);

        System.out.println("=======================================================");
        System.out.println("                   Search Account                      ");
        System.out.println("=======================================================");

        String username = promptUsername();

        int accountIndex = findAccountIndex(accounts, username);

        if (accountIndex == -1) {
            System.out.println("Account not found!");
            return;
        }

        String[] data = accounts.get(accountIndex).split("\\|", -1);

        System.out.println("-------------------------------------------------------");
        System.out.println("                  Account Details                      ");
        System.out.println("-------------------------------------------------------");

        System.out.printf("%-15s: %s%n", "Username", data[AccountIndex.USERNAME.getIndex()]);
        System.out.printf("%-15s: %s%n", "Password", data[AccountIndex.PASSWORD.getIndex()]);
        System.out.printf("%-15s: %s%n", "Role", data[AccountIndex.ROLE.getIndex()]);

        System.out.println("-------------------------------------------------------");
    }

    public void updateUserAccount(){
        List<String> accounts = new ArrayList<>();

        ACCOUNTFILEHANDLER.createAccountList(accounts);

        System.out.println("=======================================================");
        System.out.println("                   Update User Account                 ");
        System.out.println("=======================================================");

        String username = promptUsername();

        int accountToUpdate = findAccountIndex(accounts, username);

        if (accountToUpdate == -1) {
            System.out.println("Username not found!");
            return;
        }

        String[] data = accounts.get(accountToUpdate).split("\\|", -1);

        System.out.println("Account Found!");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-20s: %s%n", "Current Username", data[AccountIndex.USERNAME.getIndex()]);
        System.out.printf("%-20s: %s%n", "Current Password", data[AccountIndex.PASSWORD.getIndex()]);
        System.out.printf("%-20s: %s%n", "Current Role", data[AccountIndex.ROLE.getIndex()]);
        System.out.println("-------------------------------------------------------");
        System.out.println("1. Update Username");
        System.out.println("2. Update Password");
        System.out.println("3. Update Role");
        System.out.println("-------------------------------------------------------");

        System.out.print("Enter update option: ");
        int updateChoice = promptUpdateChoice();

        switch (updateChoice) {
            case 1:
                if(username.equals("admin") || username.equals("faculty")){
                    System.out.println("Cannot modify the usernames of original accounts.");
                    return;
                }
                boolean isRunning = true;
                String updatedUsername = "";
                while (isRunning){
                    updatedUsername = promptUsername();

                    if (data[AccountIndex.USERNAME.getIndex()].equals(updatedUsername)) {
                        System.out.println("This account already has this username!");
                        return;
                    }

                    if (ACCOUNTFILEHANDLER.usernameExists(updatedUsername)){
                        System.out.println("Username already exists! Please enter a new username.");
                        continue;
                    }

                    isRunning = false;
                }
                data[AccountIndex.USERNAME.getIndex()] = updatedUsername;
                break;
        
            case 2:
                String updatedPassword = promptPassword();

                if (data[AccountIndex.PASSWORD.getIndex()].equals(updatedPassword)) {
                    System.out.println("This account already has this password!");
                    return;
                }

                data[AccountIndex.PASSWORD.getIndex()] = updatedPassword;
                break;

            case 3:
                if(username.equals("admin") || username.equals("faculty")){
                    System.out.println("Cannot modify the roles of original accounts.");
                    return;
                }
                
                System.out.println("--------------------------------------------------------");
                System.out.println("Select New Role:");
                System.out.println("1. Admin");
                System.out.println("2. Faculty");
                System.out.println("--------------------------------------------------------");

                int roleChoice = promptRoleChoice();

                String updatedRole;

                switch (roleChoice) {
                    case 1:
                        updatedRole = "Admin";
                        break;
                    case 2:
                        updatedRole = "Faculty";
                        break;
                    default:
                        System.out.println("Invalid role option!");
                        return;
                }

                if (data[AccountIndex.ROLE.getIndex()].equalsIgnoreCase(updatedRole)) {
                    System.out.println("This account already has this role!");
                    return;
                }

                data[AccountIndex.ROLE.getIndex()] = updatedRole;
                break;

            default:
                System.out.println("Invalid update option!");
                return;
        }

        accounts.set(accountToUpdate, String.join("|", data));

        ACCOUNTFILEHANDLER.saveUpdatedAccounts(accounts);

        System.out.println("Account updated successfully!");
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
        System.out.println("Account Found!");
        System.out.println("Username: " + data[AccountIndex.USERNAME.getIndex()]);
        System.out.println("Role: " + data[AccountIndex.ROLE.getIndex()]);
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
                System.out.println("Username already exists! Please enter a new username.");
            }
            else{
                validUsername = true;
            }
        }
        return username;
    }

    public String promptPassword(){
        return VALIDATOR.getValidatedStringInput("Enter password: ", "Invalid input! Password cannot be empty and must contain at least 6 characters.", "^.{6,}$");
    }

    public int promptRoleChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Please select a role for the account (1-2): ", "Invalid input! Please enter a number between 1 and 2.", 1, 2);
    }

    public int promptUpdateChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter value to update (1-3): ", "Invalid input! Please enter a number between 1 and 3.", 1, 3);
    }

    public String promptConfirmation(){
        return VALIDATOR.getValidatedStringInput("Are you sure you want to delete this student record? (Y/N): ","Invalid input! Please enter Y or N.","^[YyNn]$");
    }

    public int findAccountIndex(List<String> accounts, String username) {

    for (int i = 0; i < accounts.size(); i++) {
        String[] data = accounts.get(i).split("\\|", -1);

        if (data[AccountIndex.USERNAME.getIndex()].trim().equalsIgnoreCase(username)) {
            return i;
        }
    }
        return -1;
    }
}
