package util;

import java.io.*;

import objects.Account;
import objects.Admin;
import objects.Faculty;

public class AccountFileHandler {
    private static AccountFileHandler instance;

    private AccountFileHandler() {}

    public static AccountFileHandler getInstance() {
        if(instance == null){
            instance = new AccountFileHandler();
        }
        return instance;
    }

    public void createAccountsFile() {
        File file = new File("ACCOUNTS.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("Accounts file created!");
            }
        } catch (IOException e) {
            System.out.println("Error creating accounts file: "
                    + e.getMessage());
        }
    }

    public void saveAccountToFile(Account account) {
        if (usernameExists(account.getUserName())) {
            System.out.println("Error: Username already exists!");
            return;
        }

        try (FileWriter writer = new FileWriter("ACCOUNTS.txt", true)) {
            String role;

            if (account instanceof Admin) {
                role = "Admin";
            } else if (account instanceof Faculty) {
                role = "Faculty";
            } else {
                System.out.println("Invalid account type!");
                return;
            }

            writer.write(account.getUserName() + "|" + account.getPassword() + "|" + role + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error: Unable to save account to file.");
        }
    }

    public boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ACCOUNTS.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] accountData = line.split("\\|");

                if (accountData.length > 0 &&
                        accountData[0].equalsIgnoreCase(username)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error: Unable to read accounts file.");
            return true;
        }

        return false;
        }   

    public String logInFromFile(String userName, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ACCOUNTS.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] accountData = line.split("\\|");

                String storedUserName = accountData[0];
                String storedPassword = accountData[1].replace("\"", "");
                String role = accountData[2].replace("\"", "");

                if (storedUserName.equals(userName) && storedPassword.equals(password)) {
                    return role;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading accounts file: " + e.getMessage());
        }   

        return null;
    }
}
