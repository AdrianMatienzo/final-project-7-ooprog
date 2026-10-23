package util;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

import models.Student;
import models.StudentIndex;

public class StudentFileHandler{
    private static final Path STUDENTS_FILE = Path.of("src", "data", "STUDENTS.txt");
    private static StudentFileHandler instance;

    private StudentFileHandler() {}

    public static StudentFileHandler getInstance() {
        if(instance == null){
            instance = new StudentFileHandler();
        }
        return instance;
    }

    public void createStudentsFile() {
        try {
            Files.createDirectories(STUDENTS_FILE.getParent());
            File file = STUDENTS_FILE.toFile();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating students file: " + e.getMessage());
        }
    }

    public void addStudent(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE.toFile(), true))) {
            writer.write(student.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error: Unable to save student to file.");
        }
    }

    public boolean studentIdExists(String studentID){
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE.toFile()))) {

        String line;

        while ((line = reader.readLine()) != null) {
            String[] studentData = line.split("\\|", -1);

            if (studentData.length > 0 &&studentData[StudentIndex.ID.getIndex()].trim().equalsIgnoreCase(studentID)) {
                return true;
            }
        }

    } catch (IOException e) {
        System.out.println("Error: Unable to read students file.");
        return true;
    }

    return false;
    }

    public void saveUpdatedStudents(List<String> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE.toFile(), false))) {

            for (String student : students) {
                writer.write(student);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating student record: " + e.getMessage());
        }
    }

    public void createStudentList(List<String> students){
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE.toFile()))) {

        String line;

        while ((line = reader.readLine()) != null) {
            students.add(line);
        }

        } catch (IOException e) {
            System.out.println("Error reading student records: " + e.getMessage());
            return;
        }

        if (students.isEmpty()) {
            return;
        }
    }
}
