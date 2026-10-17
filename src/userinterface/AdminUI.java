package userinterface;

import util.*;

public class AdminUI implements RoleMenu{
    private static InputValidation VALIDATOR = InputValidation.getInstance();

    public void roleMenu(){

        boolean isRunning = true;

        while(isRunning){
            System.out.println("=======================================================");
            System.out.println("                       Admin Menu                      ");
            System.out.println("=======================================================");

            System.out.println("1. Student Management System");
            System.out.println("2. Account Management System");
            System.out.println("3. Log Out");

            System.out.println("--------------------------------------------------------");
            int choice = VALIDATOR.getValidatedIntegerMinMax("Enter your choice: ", "Invalid input! Please enter a valid option (1-3).", 1, 3);
            switch (choice){
                case 1:
                    studentManagementSystemMenu();
                    break;

                case 2:
                    accountManagementSystemMenu();
                    break;
                    
                case 3:
                    isRunning = false;
                    System.out.println("Logging Out...\n");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @Override 
    public void studentManagementSystemMenu(){
        final StudentManagementSystemUI StudentManagementUI = new StudentManagementSystemUI(); 

        boolean isRunning = true;

        while(isRunning){
            System.out.println("=======================================================");
            System.out.println("                Student Management Menu                ");
            System.out.println("=======================================================");

            System.out.println("1. Add Student Record");
            System.out.println("2. View All Student Records");
            System.out.println("3. Search Student Record");
            System.out.println("4. Sort All Student Records");
            System.out.println("5. Filter Student Records");
            System.out.println("6. Display Number of Students");
            System.out.println("7. Update Student Record");
            System.out.println("8. Delete Student Record");
            System.out.println("9. Back to Admin Menu");

            System.out.println("--------------------------------------------------------");
            int choice = VALIDATOR.getValidatedIntegerMinMax("Enter your choice: ", "Invalid input! Please enter a valid option (1-9).", 1, 9);
            switch (choice){
                case 1:
                    StudentManagementUI.addStudentRecord();
                    break;

                case 2:
                    StudentManagementUI.viewAllStudentRecords();
                    break;

                case 3:
                    StudentManagementUI.searchStudentRecord();
                    break;

                case 4:
                    StudentManagementUI.sortAllStudentRecords();
                    break;

                case 5:
                    StudentManagementUI.filterStudentRecords();
                    break;

                case 6:
                    StudentManagementUI.displayNumberOfStudents();
                    break;

                case 7:
                    StudentManagementUI.updateStudentRecord();
                    break;

                case 8:
                    StudentManagementUI.deleteStudentRecord();
                    break;

                case 9:
                    isRunning = false;
                    System.out.println("Returning to Admin Menu...\n");
                    return;
            }
        }
    }

    @Override
    public void accountManagementSystemMenu(){
        final AccountManagementSystemUI AccountManagementUI = new AccountManagementSystemUI(); 

        boolean isRunning = true;

        while(isRunning){
            System.out.println("=======================================================");
            System.out.println("                Account Management Menu              ");
            System.out.println("=======================================================");

            System.out.println("1. Add User Account");
            System.out.println("2. View All User Accounts");
            System.out.println("3. Search User Account");
            System.out.println("4. Update User Account");
            System.out.println("5. Delete User Account");
            System.out.println("6. Back to Admin Menu");

            System.out.println("--------------------------------------------------------");
            int choice = VALIDATOR.getValidatedIntegerMinMax("Enter your choice: ", "Invalid input! Please enter a valid option (1-6).", 1, 6);
            switch (choice){
                case 1:
                    AccountManagementUI.addUserAccount();
                    break;

                case 2:
                    AccountManagementUI.viewAllUserAccounts();
                    break;

                case 3:
                    AccountManagementUI.searchUserAccount();
                    break;

                case 4:
                    AccountManagementUI.updateUserAccount();
                    break;

                case 5:
                    AccountManagementUI.deleteUserAccount();
                    break;

                case 6:
                    isRunning = false;
                    System.out.println("Returning to Admin Menu...\n");
                    return;
            }
        }
    }
}