package userinterface;

import util.InputValidation;
import util.AccountFileHandler;

public class SystemStartUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static AccountFileHandler ACCOUNTFILEHANDLER = AccountFileHandler.getInstance();

    public void start(){
        System.out.println("-------------------------------------------------------");
        System.out.println("Default Admin Username: admin");
        System.out.println("Default Admin Password: admin123");
        System.out.println("Default Faculty Username: faculty");
        System.out.println("Default Faculty Password: faculty123");
        System.out.println("-------------------------------------------------------");
        System.out.println();
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
        System.out.println("=======================================================");
        System.out.println("                      Log In Page                      ");
        System.out.println("=======================================================");
        String userName = promptUsername();
        String password = promptPassword();

        String role = ACCOUNTFILEHANDLER.logInFromFile(userName, password);

        if (role == null) {
            System.out.println("Invalid username or password.");
        } else {
            System.out.println("Login Successful! Welcome, " + userName + "!");

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
