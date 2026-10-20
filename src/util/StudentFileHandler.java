package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import objects.Student;



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
            if (file.createNewFile()) {
                System.out.println("Accounts file created!");
            }
        } catch (IOException e) {
            System.out.println("Error creating accounts file: "
                    + e.getMessage());
        }
    }

    public List<Student> loadStudents(){
        List<Student> students = new ArrayList<>();
        File file = new File("STUDENTS.txt");

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

    public void saveStudents(List<Student> students){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("STUDENTS.txt", false))) {
            for (Student student : students){
                writer.write(student.toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e){
            System.out.println("Error: Unable to save students file.");
        }
    }

    public Student findStudentById(List<Student> students, String id){
        for (Student student : students){
            if (student.getId().equalsIgnoreCase(id)){
                return student;
            }
        }
        return null;
    }

}
