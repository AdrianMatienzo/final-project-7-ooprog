package userinterface;

import java.util.List;
import java.util.ArrayList;

import strategy.*;

import models.Student;
import models.StudentIndex;

import util.StudentFileHandler;
import util.InputValidation;


public class StudentManagementSystemUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static StudentFileHandler STUDENTFILEHANDLER = StudentFileHandler.getInstance();
    
    public void addStudentRecord(){
        System.out.println("=======================================================");
        System.out.println("                 Add New Student Record                ");
        System.out.println("=======================================================");
        Student student = null;

        String id = promptNewId();
        String name = promptName();
        int age = promptAge();

        String yearLevel;

        System.out.println("-------------------------------------------------------");
        System.out.println("Select Year Level:");
        System.out.println("1. 1st Year");
        System.out.println("2. 2nd Year");
        System.out.println("3. 3rd Year");
        System.out.println("4. 4th Year");
        System.out.println("-------------------------------------------------------");

        int yearChoice = promptYearLevelChoice();
        switch (yearChoice) {
            case 1:
                yearLevel = "1st Year";
                break;
            case 2:
                yearLevel = "2nd Year";
                break;
            case 3:
                yearLevel = "3rd Year";
                break;
            case 4:
                yearLevel = "4th Year";
                break;
            default:
                System.out.println("Invalid year level option!");
                return;
        }

        String course = promptCourse();

        String status;
        System.out.println("-------------------------------------------------------");
        System.out.println("Select Student Status:");
        System.out.println("1. Active");
        System.out.println("2. Transferred");
        System.out.println("-------------------------------------------------------");

        int statusChoice = promptStatusChoice();
        switch (statusChoice) {
            case 1:
                status = "Active";
                break;
            case 2:
                status = "Transferred";
                break;
            default:
                return;
        }
        student = new Student(id, name, age, yearLevel, course, status);

        STUDENTFILEHANDLER.addStudent(student);
        System.out.println("Student record added successfully!");
    }

    public void viewAllStudentRecords(){
        List<String> students = new ArrayList<>();
        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()){
            System.out.println("No student records found!");
            return;
        }

        System.out.println("\n===============================================STUDENT RECORDS==================================================");
        printRecordsTable(students);
    }
    
    public void searchStudentRecord(){
        List<String> students = new ArrayList<>();

        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        System.out.println("=======================================================");
        System.out.println("                   Search Student                      ");
        System.out.println("=======================================================");

        String studentID = promptStudentIDSearch();

        int studentIndex = findStudentIndex(students, studentID);

        if (studentIndex == -1) {
            System.out.println("Student ID not found!");
            return;
        }

        String[] data = students.get(studentIndex).split("\\|", -1);

        System.out.println("-------------------------------------------------------");
        System.out.println("                  Student Record                       ");
        System.out.println("-------------------------------------------------------");

        System.out.printf("%-20s: %s%n", "Student ID", data[StudentIndex.ID.getIndex()]);
        System.out.printf("%-20s: %s%n", "Name", data[StudentIndex.NAME.getIndex()]);
        System.out.printf("%-20s: %s%n", "Age", data[StudentIndex.AGE.getIndex()]);
        System.out.printf("%-20s: %s%n", "Year Level", data[StudentIndex.YEAR_LEVEL.getIndex()]);
        System.out.printf("%-20s: %s%n", "Course/Program", data[StudentIndex.COURSE.getIndex()]);
        System.out.printf("%-20s: %s%n", "Enrollment Status", data[StudentIndex.STATUS.getIndex()]);
        System.out.println("-------------------------------------------------------");
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
        
        System.out.println("\n===========================================SORTED STUDENT RECORDS===============================================");
        printRecordsTable(students);
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

                    if (data[StudentIndex.YEAR_LEVEL.getIndex()].trim().equalsIgnoreCase(filterValue)) {
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

                    if (data[StudentIndex.STATUS.getIndex()].trim().equalsIgnoreCase(filterValue)) {
                        filteredStudents.add(student);
                    }
                }
                break;

            case 3:
                System.out.print("Enter Course/Program to filter: ");
                filterValue = promptCourseFilter();

                for (String student : students) {
                    String[] data = student.split("\\|", -1);

                    if (data[StudentIndex.COURSE.getIndex()].trim().equalsIgnoreCase(filterValue)) {
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

        System.out.println("\n==========================================FILTERED STUDENT RECORDS==============================================");
        printRecordsTable(filteredStudents);
    }

    public void displayNumberOfStudents(){
        final int FIRST_YEAR_INDEX = 0;
        final int SECOND_YEAR_INDEX = 1;
        final int THIRD_YEAR_INDEX = 2;
        final int FOURTH_YEAR_INDEX = 3;
        final int YEAR_COUNT = 4;

        final int ACTIVE_STATUS_INDEX = 0;
        final int INACTIVE_STATUS_INDEX = 1;
        final int TRANSFERRED_STATUS_INDEX = 2;
        final int GRADUATED_STATUS_INDEX = 3;
        final int STATUS_COUNT = 4;

        List<String> students = new ArrayList<>();
        STUDENTFILEHANDLER.createStudentList(students);

        if (students.isEmpty()){
            System.out.println("No student records found!");
            return;
        }

        int[] yearCounts = new int[YEAR_COUNT];
        int[] statusCounts = new int[STATUS_COUNT];
        int totalStudents = 0;

        for (String student : students) {
            String[] data = student.split("\\|");

            String yearLevel = data[StudentIndex.YEAR_LEVEL.getIndex()].trim();
            String status = data[StudentIndex.STATUS.getIndex()].trim();

            switch (yearLevel) {
                case "1st Year":
                    yearCounts[FIRST_YEAR_INDEX]++;
                    break;
                case "2nd Year":
                    yearCounts[SECOND_YEAR_INDEX]++;
                    break;
                case "3rd Year":
                    yearCounts[THIRD_YEAR_INDEX]++;
                    break;
                case "4th Year":
                    yearCounts[FOURTH_YEAR_INDEX]++;
                    break;
            }

            switch (status) {
                case "Active":
                    statusCounts[ACTIVE_STATUS_INDEX]++;
                    break;
                case "Inactive":
                    statusCounts[INACTIVE_STATUS_INDEX]++;
                    break;
                case "Transferred":
                    statusCounts[TRANSFERRED_STATUS_INDEX]++;
                    break;
                case "Graduated":
                    statusCounts[GRADUATED_STATUS_INDEX]++;
                    break;
            }

            totalStudents++;
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
        System.out.println("TOTAL STUDENTS: " + totalStudents);
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
        System.out.println("Student Found: " + data[StudentIndex.NAME.getIndex()]);
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

                if (data[StudentIndex.AGE.getIndex()].trim().equals(newAge)) {
                    System.out.println("Student is already " + newAge + " years old!");
                    return;
                }

                data[StudentIndex.AGE.getIndex()] = newAge;
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

                if (data[StudentIndex.YEAR_LEVEL.getIndex()].trim().equalsIgnoreCase(newYearLevel)) {
                    System.out.println("Student is already in " + newYearLevel + ".");
                    return;
                }

                data[StudentIndex.YEAR_LEVEL.getIndex()] = newYearLevel;
                break;

            case 3:
                System.out.println("-------------------------------------------------------");
                String newCourse = promptNewCourse();

                if (data[StudentIndex.COURSE.getIndex()].trim().equalsIgnoreCase(newCourse)) {
                    System.out.println("Student is already enrolled in this course/program.");
                    return;
                }

                data[StudentIndex.COURSE.getIndex()] = newCourse;
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

                if (data[StudentIndex.STATUS.getIndex()].trim().equalsIgnoreCase(newStatus)) {
                    System.out.println("Student already has the enrollment status: " + newStatus + ".");
                    return;
                }

                data[StudentIndex.STATUS.getIndex()] = newStatus;
                break;

            default:
                System.out.println("Invalid update option!");
                return;
        }

        students.set(studentToUpdate, String.join("|", data));

        STUDENTFILEHANDLER.saveUpdatedStudents(students);
        System.out.println("Student record updated successfully!");
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
        System.out.println("Student Found: " + data[StudentIndex.NAME.getIndex()]);
        System.out.println("Student ID: " + data[StudentIndex.ID.getIndex()]);
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

    public String promptNewId(){
        String id = ""; 
        boolean validId = false;

        while (!validId){
            id = VALIDATOR.getValidatedStringInput("Enter Student ID (2 numbers followed by 4 letters e.g. 12ABCD): ", "Invalid input! Username cannot be empty.", "^\\d{2}[A-Za-z]{4}$");

            if (STUDENTFILEHANDLER.studentIdExists(id)){
                System.out.println("Duplicate Username! Please enter a new username.");
            }
            else{
                validId = true;
            }
        }
        return id.toUpperCase();
    }

    public String promptName(){
        return VALIDATOR.getValidatedStringInput("Enter Student Name: ", "Invalid input! Student name cannot be empty.", "^.+$");
    }

    public int promptAge(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter Student Age (Max. 150): ", "Invalid input! Age must not be negative and cannot be empty.", 0, 150);
    }

    public int promptYearLevelChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter year level option (1-4): ", "Invalid input! Please enter a valid option (1-4).", 1, 4);
    }

    public String promptCourse(){
        return VALIDATOR.getValidatedStringInput("Enter Student's Course: ", "Invalid input! Course cannot be empty.", "^.+$");
    }

    public int promptStatusChoice(){
        return VALIDATOR.getValidatedIntegerMinMax("Enter status option (1-2): ", "Invalid input! Please enter a valid option (1-2).", 1, 2);
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
        int newAge = VALIDATOR.getValidatedIntegerMinMax("Enter Student Age (Max. 150): ", "Invalid input! Age must not be negative and cannot be empty.", 0, 150);
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

    public String promptStudentIDSearch(){
        return VALIDATOR.getValidatedStringInput("Enter Student ID: ", "Invalid input! Student ID cannot be empty and must follow this alphanumeric format (e.g. 12ABCD).", "^\\d{2}[A-Za-z]{4}$");
    }

    public int findStudentIndex(List<String> students, String studentID) {
        for (int i = 0; i < students.size(); i++) {
            String[] data = students.get(i).split("\\|", -1);

            if (data[StudentIndex.ID.getIndex()].trim().equalsIgnoreCase(studentID)) {
                return i;
            }
        }

        return -1;
    }

    public void printRecordsTable(List<String> students){
        System.out.println("================================================================================================================");

        System.out.printf("%-12s %-30s %-6s %-12s %-30s %-15s%n","Student ID", "Name", "Age", "Year Level", "Course/Program", "Status");

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        for (String student : students) {
            String[] data = student.split("\\|", -1);
            System.out.printf("%-12s %-30s %-6s %-12s %-30s %-15s%n",data[StudentIndex.ID.getIndex()],data[StudentIndex.NAME.getIndex()],data[StudentIndex.AGE.getIndex()],data[StudentIndex.YEAR_LEVEL.getIndex()], data[StudentIndex.COURSE.getIndex()], data[StudentIndex.STATUS.getIndex()]);
        }

        System.out.println("================================================================================================================");
    }
}