package userinterfaces;

import util.*;
import managers.*;
import objects.*;

public class SystemStartUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static FileHandler FILEHANDLER = FileHandler.getInstance();
    private static AccountManagementSystemManager AccountManager = AccountManagementSystemManager.getInstance(); 

    public void start(){
        AccountManager.addAccount(new Admin("admin", "admin123"));
        AccountManager.addAccount(new Faculty("faculty", "faculty123"));

        FILEHANDLER.saveAccountsToFile();
        boolean isRunning = true;

        while (isRunning){
            System.out.println("=======================================================");
            System.out.println("                Student Management System              ");
            System.out.println("=======================================================");
            System.out.println("Welcome! Please Select an option below.");
            System.out.println("-------------------------------------------------------");

            System.out.println("[1] Log In");
            System.out.println("[2] Exit System");

            System.out.println("=======================================================");

            int choice = VALIDATOR.getValidatedIntegerMinMax("Enter your choice: ", "Invalid input! Please enter a valid option (1-2).", 1, 2);

            switch (choice){
                case 1:
                    logInProcess();
                    break;

                case 2:
                    isRunning = false;
                    System.out.println("Thank you for using the Student Management System!");
                    System.exit(0);
                    break;
            }
            
        }
    }

    public void logInProcess(){
        System.out.println("\n=======================================================");
        System.out.println("                      Log In Page                      ");
        System.out.println("=======================================================");
        String userName = promptUsername();
        String password = promptPassword();

        String role = FILEHANDLER.logInFromFile(userName, password);

        if (role == null) {
            System.out.println("Invalid username or password. Please try again.\n");
        } else {
            System.out.println("Login Successful! Welcome, " + userName + "!\n");

            switch (role){
                case "Admin":
                    AdminUI adminUI = new AdminUI();
                    adminUI.roleMenu();
                    break;

                case "Faculty":
                    FacultyUI facultyUI = new FacultyUI();
                    facultyUI.roleMenu();
                    break;
                        
                default:
                    System.out.println("Invalid role.");
            }
        }
    }        

    public String promptUsername(){
        return VALIDATOR.getValidatedStringInput("Enter Username: ", "Invalid input! Username cannot be empty.", "^.+$");
    }

    public String promptPassword(){
        return VALIDATOR.getValidatedStringInput("Enter Password: ", "Invalid input! Password cannot be empty.", "^.+$");
    }
}
