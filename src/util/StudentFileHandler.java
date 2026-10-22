package util;

import java.io.*;

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
}
