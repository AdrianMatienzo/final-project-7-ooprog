package managers;

import objects.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystemManager {
    private static StudentManagementSystemManager instance;
    private List<Student> studentList = new ArrayList<>();

    private StudentManagementSystemManager() {}

    public static StudentManagementSystemManager getInstance() {
        if(instance == null){
            instance = new StudentManagementSystemManager();
        }
        return instance;
    }

    public List<Student> getStudentList() {
        return studentList;
    }


    public void addStudent(Student student) {
        studentList.add(student);
    }

    public void removeStudent(Student student) {
        studentList.remove(student);
    }
}
