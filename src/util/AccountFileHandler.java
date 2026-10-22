package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import models.Account;
import models.AccountIndex;
import models.Admin;
import models.Faculty;

public class AccountFileHandler {
    private static final Path ACCOUNTS_FILE = Path.of("src", "data", "ACCOUNTS.txt");
    private static AccountFileHandler instance;

    private AccountFileHandler() {}

    public static AccountFileHandler getInstance() {
        if(instance == null){
            instance = new AccountFileHandler();
        }
        return instance;
    }

    public void createAccountsFile() {
        try {
            Files.createDirectories(ACCOUNTS_FILE.getParent());
            File file = ACCOUNTS_FILE.toFile();
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
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE.toFile()))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] accountData = line.split("\\|");

                String storedUserName = accountData[AccountIndex.USERNAME.getIndex()];
                String storedPassword = accountData[AccountIndex.PASSWORD.getIndex()].replace("\"", "");
                String role = accountData[AccountIndex.ROLE.getIndex()].replace("\"", "");

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE.toFile(), true))) {
            writer.write(account.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error: Unable to save account to file.");
        }
    }

    public boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE.toFile()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] accountData = line.split("\\|");

                if (accountData.length > 0 &&
                        accountData[AccountIndex.USERNAME.getIndex()].equalsIgnoreCase(username)) {
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
                new FileWriter(ACCOUNTS_FILE.toFile(), false))) {

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
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE.toFile()))) {

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
