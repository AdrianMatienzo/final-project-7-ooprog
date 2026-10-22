package strategy;

import java.util.Comparator;
import models.StudentIndex;

public class SortByAge implements StudentSortStrategy {

    private boolean ascending;

    public SortByAge(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public Comparator<String> getComparator() {
        Comparator<String> comparator = Comparator.comparingInt(
            line -> Integer.parseInt(line.split("\\|", -1)[StudentIndex.AGE.getIndex()].trim())
        );

        return ascending ? comparator : comparator.reversed();
    }
}
