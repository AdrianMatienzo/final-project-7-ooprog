package userinterfaces;

import util.*;

public class FacultyUI implements RoleMenu{
    private static InputValidation VALIDATION = InputValidation.getInstance();

    public void roleMenu(){
        boolean isRunning = true;
        
        while(isRunning){
            System.out.println("=======================================================");
            System.out.println("                      Faculty Menu                     ");
            System.out.println("=======================================================");

            System.out.println("1. Student Management System");
            System.out.println("2. Log Out");

            System.out.println("--------------------------------------------------------");
            int choice = VALIDATION.getValidatedIntegerMinMax("Enter your choice: ", "Invalid input! Please enter a valid option (1-2).", 1, 2);
            switch (choice){
                case 1:
                    studentManagementSystemMenu();
                    break;

                case 2:
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

            System.out.println("1. View All Student Records");
            System.out.println("2. Search Student Record");
            System.out.println("3. Sort All Student Records");
            System.out.println("4. Filter Student Records");
            System.out.println("5. Display Number of Students");
            System.out.println("6. Update Student Record");
            System.out.println("7. Back to Faculty Menu");

            System.out.println("--------------------------------------------------------");
            int choice = VALIDATION.getValidatedIntegerMinMax("Enter your choice: ", "Invalid input! Please enter a valid option (1-9).", 1, 9);
            switch (choice){
                case 1:
                    StudentManagementUI.viewAllStudentRecords();
                    break;

                case 2:
                    StudentManagementUI.searchStudentRecord();
                    break;

                case 3:
                    StudentManagementUI.sortAllStudentRecords();
                    break;

                case 4:
                    StudentManagementUI.filterStudentRecords();
                    break;

                case 5: 
                    StudentManagementUI.displayNumberOfStudents();
                    break;

                case 6: 
                    StudentManagementUI.updateStudentRecord();
                    break;

                case 7: 
                    isRunning = false;
                    System.out.println("Returning to Faculty Menu...\n");
                    break;
                    
            }
        }
    }

    @Override 
    public void accountManagementSystemMenu(){
        System.out.println("This menu is not accessible.");
    }
}
