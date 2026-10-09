public class StudentManagementSystem {
    public void addStudent(Account student) {
        StudentsList.Students.add(student);
    }

    public void removeStudent(Account student) {
        StudentsList.Students.remove(student);
    }
}
