package strategy;

import java.util.Comparator;
import models.StudentIndex;

public class SortByName implements StudentSortStrategy {

    private boolean ascending;

    public SortByName(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public Comparator<String> getComparator() {
        Comparator<String> comparator = Comparator.comparing(
            line -> line.split("\\|", -1)[StudentIndex.NAME.getIndex()].trim(),
            String.CASE_INSENSITIVE_ORDER
        );

        return ascending ? comparator : comparator.reversed();
    }
}
