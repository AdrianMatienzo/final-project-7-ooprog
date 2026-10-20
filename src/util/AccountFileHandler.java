package util;

import java.io.*;
import java.util.List;

import models.Account;
import models.Admin;
import models.Faculty;

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
            if(file.createNewFile()){
                addAccount(new Admin("admin", "admin123"));
                addAccount(new Faculty("faculty", "faculty123"));
            }
        } catch (IOException e) {
            System.out.println("Error creating accounts file: "
                    + e.getMessage());
        }
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

    public void addAccount(Account account) {
        try (FileWriter writer = new FileWriter("ACCOUNTS.txt", true)) {
            writer.write(account.getUserName() + "|" + account.getPassword() + "|" + account.getRole() + System.lineSeparator());
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

    public void saveUpdatedAccounts(List<String> accounts) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ACCOUNTS.txt", false))) {

            for (String account : accounts) {
                writer.write(account);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating account records: "
                    + e.getMessage());
        }
    }

    public void createAccountList(List<String> accounts){
        try (BufferedReader reader = new BufferedReader(new FileReader("ACCOUNTS.txt"))) {

        String line;

        while ((line = reader.readLine()) != null) {
            accounts.add(line);
        }

        } catch (IOException e) {
            System.out.println("Error reading student records: " + e.getMessage());
            return;
        }

        if (accounts.isEmpty()) {
            return;
        }
    }
}
