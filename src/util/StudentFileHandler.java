package util;

import java.io.*;
import java.util.*;

public class StudentFileHandler{
    private static StudentFileHandler instance;

    private StudentFileHandler() {}

    public static StudentFileHandler getInstance() {
        if(instance == null){
            instance = new StudentFileHandler();
        }
        return instance;
    }

    public void createStudentsFile() {
        File file = new File("STUDENTS.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating accounts file: "
                    + e.getMessage());
        }
    }

    public void createStudentList(List<String> students){
        try (BufferedReader reader =
            new BufferedReader(new FileReader("STUDENTS.txt"))) {

        String line;

        while ((line = reader.readLine()) != null) {
            students.add(line);
        }

        } catch (IOException e) {
            System.out.println("Error reading student records: "
                    + e.getMessage());
            return;
        }

        if (students.isEmpty()) {
            return;
        }
    }

    public void saveUpdatedStudents(List<String> students) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("STUDENTS.txt", false))) {

            for (String student : students) {
                writer.write(student);
                writer.newLine();
            }

            System.out.println("Student record updated successfully!");

        } catch (IOException e) {
            System.out.println("Error updating student record: "
                    + e.getMessage());
        }
    }
}
