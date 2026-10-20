package strategy;

import java.util.List;

public class StudentSorter {

    private static final StudentSorter instance = new StudentSorter();

    private StudentSortStrategy strategy;

    private StudentSorter() {}

    public static StudentSorter getInstance() {
        return instance;
    }

    public void setStrategy(StudentSortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(List<String> students) {
        students.sort(strategy.getComparator());
    }
}