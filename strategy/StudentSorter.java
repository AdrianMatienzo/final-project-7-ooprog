package strategy;

import java.util.List;

public class StudentSorter {

    private StudentSortStrategy strategy;

    public void setStrategy(StudentSortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(List<String> students) {
        students.sort(strategy.getComparator());
    }
}