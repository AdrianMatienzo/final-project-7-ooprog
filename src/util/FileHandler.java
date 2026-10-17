package util;

import managers.*;
import objects.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler{
    private static FileHandler instance;

    private FileHandler() {}

    public static FileHandler getInstance() {
        if(instance == null){
            instance = new FileHandler();
        }
        return instance;
    }

    private static AccountManagementSystemManager AccountManager = AccountManagementSystemManager.getInstance();
    private static StudentManagementSystemManager StudentManager = StudentManagementSystemManager.getInstance();

    public void saveAccountsToFile() {
    try (FileWriter writer = new FileWriter("ACCOUNTS.txt")) {

        for (Account account : AccountManager.getAccountList()) {
            writer.write(account.toString() + "\n");
        }

        System.out.println("Accounts saved successfully!");

    } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }


    public void saveStudentsToFile() {
    try (FileWriter writer = new FileWriter("STUDENTS.txt")) {

        for (Student student : StudentManager.getStudentList()) {
            writer.write(student.toString() + "\n");
        }

        System.out.println("Students saved successfully!");

    } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public String findAccount(String searchingFor) {

        try (BufferedReader reader = new BufferedReader(new FileReader("ACCOUNTS.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", 2);

                if (data[0].equals(searchingFor)) {
                    return line;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading accounts: " + e.getMessage());
        }

        return null;
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
