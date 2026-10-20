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
        List<String> students = new ArrayList<>();
        List<String> filteredStudents = new ArrayList<>();

        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()) {
            return;
        }

        System.out.println("=======================================================");
        System.out.println("                  Filter Student Records               ");
        System.out.println("=======================================================");
        System.out.println("1. Filter by Year Level");
        System.out.println("2. Filter by Enrollment Status");
        System.out.println("3. Filter by Course/Program");
        System.out.println("-------------------------------------------------------");

        System.out.print("Enter your choice: ");
        int filterChoice = promptSortChoice();

        String filterValue;

        switch (filterChoice) {
            case 1:
                System.out.println("-------------------------------------------------------");
                System.out.println("Select Year Level");
                System.out.println("1. 1st Year");
                System.out.println("2. 2nd Year");
                System.out.println("3. 3rd Year");
                System.out.println("4. 4th Year");
                System.out.println("-------------------------------------------------------");

                System.out.print("Enter your choice: ");
                int yearChoice = promptYearLevelFilterChoice();

                switch (yearChoice) {
                    case 1:
                        filterValue = "1st Year";
                        break;
                    case 2:
                        filterValue = "2nd Year";
                        break;
                    case 3:
                        filterValue = "3rd Year";
                        break;
                    case 4:
                        filterValue = "4th Year";
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                        return;
                }

                for (String student : students) {
                    String[] data = student.split("\\|", -1);

                    if (data[3].trim().equalsIgnoreCase(filterValue)) {
                        filteredStudents.add(student);
                    }
                }

                
                break;

            case 2:
                System.out.println("-------------------------------------------------------");
                System.out.println("Select Enrollment Status");
                System.out.println("1. Active");
                System.out.println("2. Inactive");
                System.out.println("3. Transferred");
                System.out.println("4. Graduated");
                System.out.println("-------------------------------------------------------");

                System.out.print("Enter your choice: ");
                int statusChoice = promptStatusFilterChoice();

                switch (statusChoice) {
                    case 1:
                        filterValue = "Active";
                        break;
                    case 2:
                        filterValue = "Inactive";
                        break;
                    case 3:
                        filterValue = "Transferred";
                        break;
                    case 4:
                        filterValue = "Graduated";
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                        return;
                }

                for (String student : students) {
                    String[] data = student.split("\\|", -1);

                    if (data[5].trim().equalsIgnoreCase(filterValue)) {
                        filteredStudents.add(student);
                    }
                }
                break;

            case 3:
                System.out.print("Enter Course/Program to filter: ");
                filterValue = promptCourseFilter();

                for (String student : students) {
                    String[] data = student.split("\\|", -1);

                    if (data[4].trim().equalsIgnoreCase(filterValue)) {
                        filteredStudents.add(student);
                    }
                }
                break;

            default:
                System.out.println("Invalid Choice!");
                return;
        }

        if (filteredStudents.isEmpty()) {
            System.out.println("No matching student records found!");
            return;
        }

        System.out.println("\nFiltered Student Records");
        System.out.println("================================================================================================================");
        System.out.printf("%-12s %-22s %-6s %-12s %-25s %-15s%n",
                "Student ID", "Name", "Age", "Year Level",
                "Course/Program", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        for (String student : filteredStudents) {
            String[] data = student.split("\\|", -1);

            System.out.printf("%-12s %-22s %-6s %-12s %-25s %-15s%n",
                    data[0], data[1], data[2], data[3], data[4], data[5]);
        }

        System.out.println("================================================================================================================");
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
        List<String> students = new ArrayList<>();

        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        System.out.println("=======================================================");
        System.out.println("                  Update Student Record                ");
        System.out.println("=======================================================");

        String studentID = promptStudentIDSearch();

        int studentToUpdate = findStudentIndex(students, studentID);

        if (studentToUpdate == -1) {
            System.out.println("Student ID not found!");
            return;
        }

        String[] data = students.get(studentToUpdate).split("\\|", -1);

        System.out.println("-------------------------------------------------------");
        System.out.println("Student Found: " + data[1]);
        System.out.println("-------------------------------------------------------");
        System.out.println("1. Update Age");
        System.out.println("2. Update Year Level");
        System.out.println("3. Update Course/Program");
        System.out.println("4. Update Enrollment Status");
        System.out.println("-------------------------------------------------------");

        int updateChoice = promptUpdateChoice();

        switch (updateChoice) {
            case 1:
                System.out.println("-------------------------------------------------------");
                String newAge = promptNewAge();

                if (data[2].trim().equals(newAge)) {
                    System.out.println("Student is already " + newAge + " years old!");
                    return;
                }

                data[2] = newAge;
                break;

            case 2:
                System.out.println("-------------------------------------------------------");
                System.out.println("Select New Year Level");
                System.out.println("1. 1st Year");
                System.out.println("2. 2nd Year");
                System.out.println("3. 3rd Year");
                System.out.println("4. 4th Year");
                System.out.println("-------------------------------------------------------");

                int yearChoice = promptNewYearLevelChoice();
                String newYearLevel;
                switch (yearChoice) {
                    case 1:
                        newYearLevel = "1st Year";
                        break;
                    case 2:
                        newYearLevel = "2nd Year";
                        break;
                    case 3:
                        newYearLevel = "3rd Year";
                        break;
                    case 4:
                        newYearLevel = "4th Year";
                        break;
                    default:
                        System.out.println("Invalid year level option!");
                        return;
                }

                if (data[3].trim().equalsIgnoreCase(newYearLevel)) {
                    System.out.println("Student is already in " + newYearLevel + "!");
                    return;
                }

                data[3] = newYearLevel;
                break;

            case 3:
                System.out.println("-------------------------------------------------------");
                String newCourse = promptNewCourse();

                if (data[4].trim().equalsIgnoreCase(newCourse)) {
                    System.out.println("Student is already enrolled in this course/program!");
                    return;
                }

                data[4] = newCourse;
                break;

            case 4:
                System.out.println("-------------------------------------------------------");
                System.out.println("Select New Enrollment Status");
                System.out.println("1. Active");
                System.out.println("2. Inactive");
                System.out.println("3. Transferred");
                System.out.println("4. Graduated");
                System.out.println("-------------------------------------------------------");

                int statusChoice = promptNewStatusChoice();

                String newStatus;

                switch (statusChoice) {
                    case 1:
                        newStatus = "Active";
                        break;
                    case 2:
                        newStatus = "Inactive";
                        break;
                    case 3:
                        newStatus = "Transferred";
                        break;
                    case 4:
                        newStatus = "Graduated";
                        break;
                    default:
                        System.out.println("Invalid enrollment status option!");
                        return;
                }

                if (data[5].trim().equalsIgnoreCase(newStatus)) {
                    System.out.println("Student already has the enrollment status: "
                            + newStatus + "!");
                    return;
                }

                data[5] = newStatus;
                break;

            default:
                System.out.println("Invalid update option!");
                return;
        }

        students.set(studentToUpdate, String.join("|", data));

        STUDENTFILEHANDLER.saveUpdatedStudents(students);
    }

    public void deleteStudentRecord(){
        List<String> students = new ArrayList<>();

        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        System.out.println("=======================================================");
        System.out.println("                  Delete Student Record                ");
        System.out.println("=======================================================");

        String studentID = promptStudentIDSearch();

        int studentToDelete = findStudentIndex(students, studentID);

        if (studentToDelete == -1) {
            System.out.println("Student ID not found!");
            return;
        }

        String[] data = students.get(studentToDelete).split("\\|", -1);

        System.out.println("-------------------------------------------------------");
        System.out.println("Student Found: " + data[1]);
        System.out.println("Student ID: " + data[0]);
        System.out.println("-------------------------------------------------------");

        String confirmation = promptConfirmation();

        if (confirmation.equalsIgnoreCase("N")) {
            System.out.println("Student record deletion cancelled!");
            return;
        }

        students.remove(studentToDelete);
        STUDENTFILEHANDLER.saveUpdatedStudents(students);

        System.out.println("Student record deleted successfully!");
    }

    public String promptStudentIDSearch(){
        return VALIDATOR.getValidatedStringInput("Enter Student ID: ", "Invalid input! Student ID cannot be empty.", "^.+$");
    }
    
    public int promptSortChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter sorting method (1-3): ", "Invalid input! Please enter a valid option (1-3).", 1, 3);
    }

    public int promptOrderChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter sorting order (1-2): ", "Invalid input! Please enter a valid option (1-2).", 1, 2);
    }

    public int promptFilterChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter filtering method (1-3): ", "Invalid input! Please enter a valid option (1-3).", 1, 3);
    }

    public int promptYearLevelFilterChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter year level option (1-4): ", "Invalid input! Please enter a valid option (1-4).", 1, 4);
    }

    public int promptStatusFilterChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter enrollment status option (1-4): ", "Invalid input! Please enter a valid option (1-4).", 1, 4);
    }

    public String promptCourseFilter(){
        return VALIDATOR.getValidatedStringInput("Enter Course/Program to filter: ", "Invalid input! Course cannot be empty.", "^.+$");
    }

    public int promptUpdateChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter value to update (1-4): ", "Invalid input! Please enter a valid option (1-4).", 1, 4);
    }

    public String promptNewAge(){
        int newAge = VALIDATOR.getValidatedIntegerMin("Enter Student Age: ", "Invalid input! Age must not be negative and cannot be empty.", 0);
        return Integer.toString(newAge);
    }

    public int promptNewYearLevelChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter year level option (1-4): ", "Invalid input! Please enter a valid option (1-4).", 1, 4);
    }

    public String promptNewCourse(){
        return VALIDATOR.getValidatedStringInput("Enter New Course: ", "Invalid input! Course cannot be empty.", "^.+$");
    }

    public int promptNewStatusChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter enrollment status option (1-4): ", "Invalid input! Please enter a valid option (1-4).", 1, 4);
    }

    public String promptConfirmation(){
        return VALIDATOR.getValidatedStringInput("Are you sure you want to delete this student record? (Y/N): ","Invalid input! Please enter Y or N.","^[YyNn]$");
    }

    public int findStudentIndex(List<String> students, String studentID) {
        for (int i = 0; i < students.size(); i++) {
            String[] data = students.get(i).split("\\|", -1);

            if (data[0].trim().equalsIgnoreCase(studentID)) {
                return i;
            }
        }

        return -1;
    }
}