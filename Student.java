public class Student {
    private String id;
    private String name;
    private int age;
    private String yearLevel;
    private String course;
    private String status;
    
    public Student(String id, String name, int age, String yearLevel, String course, String status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.yearLevel = yearLevel;
        this.course = course;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public String getCourse() {
        return course;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
