package userinterface;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import objects.Student;
import util.InputValidation;
import util.StudentFileHandler;


public class StudentManagementSystemUI{
    private static InputValidation VALIDATOR = InputValidation.getInstance();
    private static StudentFileHandler STUDENTFILEHANDLER = StudentFileHandler.getInstance();

    private static final String STUDENTS_FILE = "STUDENTS.txt";
    private static final String[] YEAR_LEVELS = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
    private static final String[] ENROLLMENT_STATUS = {"Active", "Inactive", "Transferred", "Graduated"};
    private static final int MIN_AGE = 6;
    private static final int MAX_AGE = 100;


    public void addStudentRecord(){

    }

    public void viewAllStudentRecords(){

    }

    public void searchStudentRecord(){

    }

    public void sortAllStudentRecords(){

    }

    public void filterStudentRecords(){
        List<Student> students = loadStudents();

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        boolean isFiltering = true;

        while (isFiltering) {
            System.out.println("=======================================================");
            System.out.println("                Filter Student Records                 ");
            System.out.println("=======================================================");
            System.out.println("1. Filter by Year Level");
            System.out.println("2. Filter by Course/Program");
            System.out.println("3. Filter by Enrollment Status");
            System.out.println("4. Back to Student Management Menu");
            System.out.println("--------------------------------------------------------");

            int choice = VALIDATOR.getValidatedIntegerMinMax("Enter your choice: ", 
            "Invalid input! Please enter a valid option (1-4).", 1, 4);

            switch (choice) {

                case 1:
                    String yearLevel = promptYearLevel();
                    displayFilteredResults(students, "Year Level: " + yearLevel, 1, yearLevel);
                    break;

                case 2:
                    String course = promptCourseFromExisting(students);
                    displayFilteredResults(students, "Course/Program: " + course, 2, course);
                    break;

                case 3:
                    String status = promptEnrollmentStatus();
                    displayFilteredResults(students, "Enrollment Status: " + status, 3, status);
                    break;

                case 4:
                    isFiltering = false;
                    System.out.println("Returning to Student Management Menu...\n");
                    break;
            }
        }
    }

    public void displayNumberOfStudents(){

    }

    public void updateStudentRecord(){
        System.out.println("=======================================================");
        System.out.println("                Update Student Record                  ");
        System.out.println("=======================================================");

        List<Student> students = loadStudents();

        if (students.isEmpty()){
            System.out.println("No student records found.\n");
            return;
        }

        String id = VALIDATOR.getValidatedStringInput("Enter Student ID to update: ", "Invalid input! Student ID cannot be empty.", "^\\s*\\S.*$").trim();
        Student student = findStudentById(students, id);

        if (student == null){
            System.out.println("Student record not found.\n");
            return;
        }

        boolean isUpdating = true;

        while (isUpdating){
            System.out.println("--------------------------------------------------------");
            System.out.println("Current Record:");
            printStudentDetails(student);
            System.out.println("--------------------------------------------------------");
            System.out.println("What would you like to update?");
            System.out.println("1. Age");
            System.out.println("2. Year Level");
            System.out.println("3. Course/Program");
            System.out.println("4. Enrollment Status");
            System.out.println("5. Done");
            System.out.println("--------------------------------------------------------");

            int choice = VALIDATOR.getValidatedIntegerMinMax("Enter your choice: ", 
            "Invalid input! Please enter a valid option (1-5).", 1, 5);

            switch (choice) {
                case 1:
                    int age = VALIDATOR.getValidatedIntegerMinMax("Enter new age (" + MIN_AGE + "-" + MAX_AGE + "): ",
                            "Invalid input! Please enter a number between " + MIN_AGE + " and " + MAX_AGE + ".",
                            MIN_AGE, MAX_AGE);
                    student.setAge(age);
                    saveStudents(students);
                    System.out.println("Age updated successfully!");
                    break;

                case 2:
                    student.setYearLevel(promptYearLevel());
                    saveStudents(students);
                    System.out.println("Year level updated successfully!");
                    break;

                case 3:
                    String course = VALIDATOR.getValidatedStringInput("Enter new course/program: ", "Invalid input! Course/Program cannot be empty.", "^[^|]*\\S[^|]*$").trim();
                    student.setCourse(course);
                    saveStudents(students);
                    System.out.println("Course/Program updated successfully!");
                    break;

                case 4:
                    student.setStatus(promptEnrollmentStatus());
                    saveStudents(students);
                    System.out.println("Enrollment status updated successfully!");
                    break;

                case 5:
                    isUpdating = false;
                    break;
            }
        }
    }

    public void deleteStudentRecord(){
        System.out.println("=======================================================");
        System.out.println("                Delete Student Record                  ");
        System.out.println("=======================================================");

        List<Student> students = loadStudents();

        if (students.isEmpty()){
            System.out.println("No student records found.\n");
            return;
        }

        String id = VALIDATOR.getValidatedStringInput("Enter Student ID to delete: ", 
        "Invalid input! Student ID cannot be empty.", "^\\s*\\S.*$").trim();
        Student student = findStudentById(students, id);

        if (student == null) {
            System.out.println("Student not found.\n");
            return;
        }

        System.out.println("--------------------------------------------------------");
        System.out.println("Student ID : " + student.getId());
        System.out.println("Name       : " + student.getName());
        System.out.println("--------------------------------------------------------");

        String confirm = VALIDATOR.getValidatedStringInput(
            "Are you sure you want to delete this record? (Y/N): ",
            "Invalid input! Please enter Y or N.",
            "^[YyNn]$");

        if (confirm.equalsIgnoreCase("Y")){
            students.remove(student);
            saveStudents(students);
            System.out.println("Student record deleted successfully!\n");
        } else {
            System.out.println("Deletion cancelled.\n");
        }

    }

    private List<Student> loadStudents(){
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENTS_FILE);

        if (!file.exists()){
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()){
                    continue;
                }

                String[] data = line.split("\\|");
                if (data.length < 6){
                    continue;
                }

                try {
                    students.add(new Student(
                        data[0].trim(),
                        data[1].trim(),
                        Integer.parseInt(data[2].trim()),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim()));
                } catch (NumberFormatException e){
                    // skip malformed line
                }
            }
        } catch (IOException e){
            System.out.println("Error: Unable to read students file.");
        }

        return students;
    }

    private void saveStudents(List<Student> students){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE, false))) {
            for (Student student : students){
                writer.write(student.toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e){
            System.out.println("Error: Unable to save students file.");
        }
    }

    private Student findStudentById(List<Student> students, String id){
        for (Student student : students){
            if (student.getId().equalsIgnoreCase(id)){
                return student;
            }
        }
        return null;
    }

    private String promptYearLevel(){
        System.out.println("Year Level:");
        for (int i = 0; i < YEAR_LEVELS.length; i++){
            System.out.println((i + 1) + ". " + YEAR_LEVELS[i]);
        }
        int choice = VALIDATOR.getValidatedIntegerMinMax("Select year level (1-4): ", "Invalid input! Please enter a number between 1 and 4.", 1, 4);
        return YEAR_LEVELS[choice - 1];
    }

    private String promptEnrollmentStatus(){
        System.out.println("Enrollment Status:");
        for (int i = 0; i < ENROLLMENT_STATUS.length; i++){
            System.out.println((i + 1) + ". " + ENROLLMENT_STATUS[i]);
        }
        int choice = VALIDATOR.getValidatedIntegerMinMax("Select enrollment status (1-4): ", "Invalid input! Please enter a number between 1 and 4.", 1, 4);
        return ENROLLMENT_STATUS[choice - 1];
    }

    private String promptCourseFromExisting(List<Student> students){
        List<String> courses = new ArrayList<>();

        for (Student student : students){
            boolean found = false;
            for (String c : courses){
                if (c.equalsIgnoreCase(student.getCourse())){
                    found = true;
                    break;
                }
            }
            if (!found){
                courses.add(student.getCourse());
            }
        }

        System.out.println("Courses/Programs:");
        for (int i = 0; i < courses.size(); i++){
            System.out.println((i + 1) + ". " + courses.get(i));
        }

        int choice = VALIDATOR.getValidatedIntegerMinMax(
            "Select course/program (1-" + courses.size() + "): ",
            "Invalid input! Please enter a number between 1 and " + courses.size() + ".",
            1, courses.size());
        return courses.get(choice - 1);
    }

    private void displayFilteredResults(List<Student> students, String criteria, int field, String value){
        List<Student> results = new ArrayList<>();

        for (Student student : students){
            String actual;
            switch (field){
                case 1:  actual = student.getYearLevel(); break;
                case 2:  actual = student.getCourse();    break;
                default: actual = student.getStatus();    break;
            }
            if (actual.equalsIgnoreCase(value)){
                results.add(student);
            }
        }

        System.out.println("\n=======================================================");
        System.out.println("Filter Results - " + criteria);
        System.out.println("=======================================================");

        if (results.isEmpty()){
            System.out.println("No student records match this filter.\n");
            return;
        }

        String format = "%-12s %-22s %-5s %-10s %-26s %-12s%n";
        System.out.printf(format, "ID", "Name", "Age", "Year", "Course/Program", "Status");
        System.out.println("---------------------------------------------------------------------------------------");

        for (Student student : results){
            System.out.printf(format, student.getId(), student.getName(), student.getAge(),
                student.getYearLevel(), student.getCourse(), student.getStatus());
        }

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Total records found: " + results.size() + "\n");
    }

    private void printStudentDetails(Student student){
        System.out.println("Student ID : " + student.getId());
        System.out.println("Name       : " + student.getName());
        System.out.println("Age        : " + student.getAge());
        System.out.println("Year Level : " + student.getYearLevel());
        System.out.println("Course     : " + student.getCourse());
        System.out.println("Status     : " + student.getStatus());
    }

}