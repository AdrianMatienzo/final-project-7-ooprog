package userinterface;

import java.util.*;
import strategy.*;

import util.StudentFileHandler;
import util.InputValidation;


public class StudentManagementSystemUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static StudentFileHandler STUDENTFILEHANDLER = StudentFileHandler.getInstance();

    public void addStudentRecord(){

    }

    public void viewAllStudentRecords(){

    }

    public void searchStudentRecord(){

    }

    public void sortAllStudentRecords(){
        List<String> students = new ArrayList<>();
        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()){
            System.out.println("No student records found!");
            return;
        }
        System.out.println("=======================================================");
        System.out.println("                   Sort Student Records                ");
        System.out.println("=======================================================");
        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by Age");
        System.out.println("3. Sort by Year Level");
        System.out.println("-------------------------------------------------------");

        int choice = promptSortChoice();
        int orderChoice;

        StudentSortStrategy strategy = null;
        boolean ascending = false;


        switch (choice){
            case 1:
                System.out.println("-------------------------------------------------------");  
                System.out.println("Sort by Name");
                System.out.println("1. A-Z (Ascending)");
                System.out.println("2. Z-A (Descending)");
                System.out.println("-------------------------------------------------------");  

                orderChoice = promptOrderChoice();

                if (orderChoice == 1){
                    ascending = true;
                }

                strategy = new SortByName(ascending);

                break;
            case 2:
                System.out.println("-------------------------------------------------------");  
                System.out.println("Sort by Age");
                System.out.println("1. Ascending (Youngest to Oldest)");
                System.out.println("2. Descending (Oldest to Youngest)");
                System.out.println("-------------------------------------------------------"); 

                orderChoice = promptOrderChoice();

                if (orderChoice == 1) {
                    ascending = true;
                }

                strategy = new SortByAge(ascending);
                break;
            case 3:
                System.out.println("-------------------------------------------------------");  
                System.out.println("Sort by Year Level");
                System.out.println("1. Ascending (1st to 4th Year)");
                System.out.println("2. Descending (4th to 1st Year)");
                System.out.println("-------------------------------------------------------");  

                orderChoice = promptOrderChoice();

                if (orderChoice == 1) {
                    ascending = true;
                }

                strategy = new SortByYearLevel(ascending);
                break;
            default:
                System.out.println("Invalid Choice!");
        }

        StudentSorter sorter = StudentSorter.getInstance();
        sorter.setStrategy(strategy);
        sorter.sort(students);
        
        System.out.println("\nSorted Student Records");
        System.out.println("================================================================================================================");

        System.out.printf(
            "%-12s %-22s %-6s %-12s %-25s %-15s%n",
            "Student ID", "Name", "Age", "Year Level", "Course/Program", "Status"
        );

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        for (String student : students) {
            String[] data = student.split("\\|", -1);

            System.out.printf("%-12s %-22s %-6s %-12s %-25s %-15s%n",data[0],data[1],data[2],data[3], data[4], data[5]);
        }

        System.out.println("================================================================================================================");
            }

    public void filterStudentRecords(){

    }

    public void displayNumberOfStudents(){
        List<String> students = new ArrayList<>();
        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()){
            System.out.println("No student records found!");
            return;
        }

        int[] yearCounts = new int[4];
        int[] statusCounts = new int[4];
        int[] totalStudents = new int[1];

        for (String student : students) {
            String[] data = student.split("\\|");

            String yearLevel = data[3].trim();
            String status = data[5].trim();

            switch (yearLevel) {
                case "1st Year":
                    yearCounts[0]++;
                    break;
                case "2nd Year":
                    yearCounts[1]++;
                    break;
                case "3rd Year":
                    yearCounts[2]++;
                    break;
                case "4th Year":
                    yearCounts[3]++;
                    break;
            }

            switch (status) {
                case "Active":
                    statusCounts[0]++;
                    break;
                case "Inactive":
                    statusCounts[1]++;
                    break;
                case "Transferred":
                    statusCounts[2]++;
                    break;
                case "Graduated":
                    statusCounts[3]++;
                    break;
            }

            totalStudents[0]++;
        }

        System.out.println("=======================================================");
        System.out.println("                 STUDENT STATISTICS");
        System.out.println("=======================================================");

         System.out.println("-------------------------------------------------------");
        System.out.println("Students by Year Level");
        System.out.println("-------------------------------------------------------");

        String[] yearLabels = {"1st Year", "2nd Year", "3rd Year", "4th Year"};

        for (int i = 0; i < yearCounts.length; i++) {
            System.out.println(yearLabels[i] + ": " + yearCounts[i]);
        }

        System.out.println("-------------------------------------------------------");
        System.out.println("Students by Enrollment Status");
        System.out.println("-------------------------------------------------------");

        String[] statusLabels = {"Active", "Inactive", "Transferred", "Graduated"};

        for (int i = 0; i < statusCounts.length; i++) {
            System.out.println(statusLabels[i] + ": " + statusCounts[i]);
        }

        System.out.println("=======================================================");
        System.out.println("TOTAL STUDENTS: " + totalStudents[0]);
        System.out.println("=======================================================");
    }

    public void updateStudentRecord(){

    }

    public void deleteStudentRecord(){

    }

    public int promptSortChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter your choice (1-3): ", "Invalid input! Please enter a valid option (1-3).", 1, 3);
    }

    public int promptOrderChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter your choice (1-2): ", "Invalid input! Please enter a valid option (1-2).", 1, 2);
    }
}